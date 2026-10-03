package power_monitor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class TelegramBotService extends TelegramLongPollingBot {

    private final DeviceService deviceService;
    private final String botUsername;
    private Long chatId;

    public TelegramBotService(
            DeviceService deviceService,
            @Value("${telegram.bot.token}") String botToken,
            @Value("${telegram.bot.username}") String botUsername) {
        super(botToken);
        this.deviceService = deviceService;
        this.botUsername = botUsername;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.hasMessage() && update.getMessage().hasText()) {
            this.chatId = update.getMessage().getChatId();

            String messageText = update.getMessage().getText();
            long currentChatId = update.getMessage().getChatId();

            if (messageText.equals("/start") || messageText.equals("/status")) {
                sendDeviceStatus(currentChatId);
            }
        }
    }

    public Long getChatId() {
        return chatId;
    }

    public void sendDeviceStatus(long chatId) {
        StringBuilder response = new StringBuilder("Статус пристроїв:\n\n");
        for (Device device : deviceService.getDevices()) {
            String statusIcon = device.isOnline() ? "🟢" : "🔴";
            String statusText = device.isOnline() ? "Світло є" : "Світла немає";
            String timeLabel = device.isOnline() ? "Світло є: " : "Світла немає: ";
            String durationStr = device.getFormattedDuration();
            response.append("• ").append(device.getName()).append(" (").append(device.getAddress()).append(")\n")
                    .append("  Статус: ").append(statusIcon).append(" ").append(statusText).append("\n")
                    .append("  ").append(timeLabel).append(durationStr).append("\n\n");
        }
        sendMessage(chatId, response.toString());
    }

    public void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private String formatDuration(LocalDateTime startTime) {
        if (startTime == null) return "невідомо";
        Duration duration = Duration.between(startTime, LocalDateTime.now());
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        if (hours > 0) {
            return hours + " год. " + minutes + " хв.";
        } else {
            return minutes + " хв.";
        }
    }
}