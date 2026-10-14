import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class AIChatbot extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private Map<String, String> knowledgeBase;

    public AIChatbot() {
        // Window setup
        setTitle("CodeAlpha AI Chatbot");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initKnowledgeBase();
        initUI();
    }

    // Pre-trained rule-based FAQ dataset
    private void initKnowledgeBase() {
        knowledgeBase = new HashMap<>();
        knowledgeBase.put("hello", "Hello! How can I assist you today?");
        knowledgeBase.put("hi", "Hi there! What can I help you with?");
        knowledgeBase.put("how are you", "I am an AI, running smoothly and ready to assist you!");
        knowledgeBase.put("name", "I am CodeAlpha Support Bot, your virtual assistant.");
        knowledgeBase.put("internship", "CodeAlpha offers internships in Java, Web Development, Python, and more.");
        knowledgeBase.put("certificate", "Certificates are QR-verified and awarded upon successfully completing the required tasks.");
        knowledgeBase.put("help", "You can ask about CodeAlpha internships, certificates, date & time, or say hello!");
        knowledgeBase.put("bye", "Goodbye! Have a great and productive day ahead.");
    }

    // GUI components initialization
    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        // Header Title
        JLabel headerLabel = new JLabel("CodeAlpha Virtual Assistant", SwingConstants.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(headerLabel, BorderLayout.NORTH);

        // Chat display area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom input field and button panel
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputField = new JTextField();
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sendButton = new JButton("Send");
        sendButton.setFont(new Font("SansSerif", Font.BOLD, 14));

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        // Action listener for Enter key and button click
        ActionListener sendAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processMessage();
            }
        };

        sendButton.addActionListener(sendAction);
        inputField.addActionListener(sendAction);

        // Welcome message
        chatArea.append("Bot: Hello! I am your AI Assistant. Ask me anything or type 'help'!\n");
    }

    // Handle user input
    private void processMessage() {
        String userInput = inputField.getText().trim();
        if (userInput.isEmpty()) return;

        chatArea.append("\nYou: " + userInput + "\n");
        inputField.setText("");

        String response = generateResponse(userInput);
        chatArea.append("Bot: " + response + "\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    // NLP intent matching logic
    private String generateResponse(String input) {
        String cleaned = input.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "");

        // Dynamic intent: Date/Time check
        if (cleaned.contains("time") || cleaned.contains("date")) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return "Current date and time is: " + dtf.format(LocalDateTime.now());
        }

        // Search through pre-trained keywords
        for (Map.Entry<String, String> entry : knowledgeBase.entrySet()) {
            if (cleaned.contains(entry.getKey())) {
                return entry.getValue();
            }
        }

        // Default response if no intent matches
        return "I am sorry, I do not understand that yet. You can ask about our internship, tasks, certificates, or type 'help'.";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AIChatbot bot = new AIChatbot();
            bot.setVisible(true);
        });
    }
}