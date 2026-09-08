package atm_interface;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.text.NumberFormat;
import java.util.Locale;

public class ATMInterface {

    private final BankAccount userAccount;
    private final ATM atm;
    private final NumberFormat currencyFormat;

    private final VBox mainLayout;

    private VBox pinPanel;
    private VBox menuPanel;
    private VBox transactionPanel;

    private PasswordField pinPasswordField;
    private Text transactionMessage;
    private Text amountMessage;

    private Text balanceText;
    private Text maskedBalanceText;

    private TextField amountField;

    private String currentTransaction = "";
    private boolean isLoggedIn = false;

    public ATMInterface() {
        userAccount = new BankAccount(
                "1234-5678-9012-3456",
                "John Doe",
                1500.00
        );

        atm = new ATM(userAccount);

        currencyFormat = NumberFormat.getCurrencyInstance(
                new Locale("en", "US")
        );

        mainLayout = new VBox(18);
        createUI();
    }

    public VBox getRoot() {
        return mainLayout;
    }

    private void createUI() {
        mainLayout.setPadding(new Insets(25));
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.getStyleClass().add("main-layout");

        Text title = new Text("🏧 ATM MACHINE");
        title.getStyleClass().add("app-title");

        Text subtitle = new Text(
                "Secure Banking at Your Fingertips"
        );
        subtitle.getStyleClass().add("app-subtitle");

        VBox card = createCard();
        pinPanel = createPinPanel();
        menuPanel = createMenuPanel();
        transactionPanel = createTransactionPanel();

        menuPanel.setVisible(false);
        menuPanel.setManaged(false);

        transactionPanel.setVisible(false);
        transactionPanel.setManaged(false);

        mainLayout.getChildren().addAll(
                title,
                subtitle,
                card,
                pinPanel,
                menuPanel,
                transactionPanel
        );

        updateBalanceDisplay();
    }

    private VBox createCard() {
        VBox card = new VBox(10);
        card.setPadding(new Insets(18));
        card.setPrefWidth(520);
        card.getStyleClass().add("bank-card");

        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label chip = new Label("▦");
        chip.getStyleClass().add("card-chip");

        Label cardType = new Label("DEBIT");
        cardType.getStyleClass().add("card-type");

        HBox.setMargin(
                cardType,
                new Insets(0, 0, 0, 12)
        );

        topRow.getChildren().addAll(
                chip,
                cardType
        );

        Text cardNumber = new Text(
                "****  ****  ****  3456"
        );
        cardNumber.getStyleClass().add("card-number");

        HBox holderRow = new HBox(40);
        holderRow.setAlignment(Pos.CENTER_LEFT);

        VBox holderBox = new VBox(3);

        Label holderLabel = new Label("CARD HOLDER");
        holderLabel.getStyleClass().add("card-label");

        Text holderText = new Text(
                userAccount.getAccountHolder().toUpperCase()
        );
        holderText.getStyleClass().add("card-value");

        holderBox.getChildren().addAll(
                holderLabel,
                holderText
        );

        VBox expiryBox = new VBox(3);

        Label expiryLabel = new Label("EXPIRES");
        expiryLabel.getStyleClass().add("card-label");

        Text expiryText = new Text("12/26");
        expiryText.getStyleClass().add("card-value");

        expiryBox.getChildren().addAll(
                expiryLabel,
                expiryText
        );

        holderRow.getChildren().addAll(
                holderBox,
                expiryBox
        );

        HBox balanceRow = new HBox(10);
        balanceRow.setAlignment(Pos.CENTER_LEFT);

        Label balanceLabel = new Label("BALANCE");
        balanceLabel.getStyleClass().add("card-label");

        balanceText = new Text();
        balanceText.getStyleClass().add("card-balance");

        maskedBalanceText = new Text("••••••");
        maskedBalanceText.getStyleClass().add(
                "card-balance"
        );
        maskedBalanceText.setVisible(false);
        maskedBalanceText.setManaged(false);

        Button toggleButton = new Button("◉");
        toggleButton.getStyleClass().add(
                "balance-toggle"
        );
        toggleButton.setOnAction(
                e -> toggleBalanceVisibility()
        );

        balanceRow.getChildren().addAll(
                balanceLabel,
                balanceText,
                maskedBalanceText,
                toggleButton
        );

        card.getChildren().addAll(
                topRow,
                cardNumber,
                holderRow,
                balanceRow
        );

        return card;
    }

    private VBox createPinPanel() {
        VBox panel = new VBox(14);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(18));
        panel.getStyleClass().add("panel");

        Label title = new Label("Enter Your PIN");
        title.getStyleClass().add("panel-title");

        pinPasswordField = new PasswordField();
        pinPasswordField.setPromptText("••••");
        pinPasswordField.setPrefWidth(180);
        pinPasswordField.setMaxWidth(180);
        pinPasswordField.getStyleClass().add(
                "pin-field"
        );

        GridPane keypad = createPinKeypad();

        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER);

        Button loginButton = new Button("🔑 Login");
        loginButton.getStyleClass().add(
                "success-button"
        );
        loginButton.setOnAction(
                e -> attemptLogin()
        );

        Button clearButton = new Button("Clear");
        clearButton.getStyleClass().add(
                "danger-button"
        );
        clearButton.setOnAction(
                e -> clearPin()
        );

        actions.getChildren().addAll(
                loginButton,
                clearButton
        );

        transactionMessage = new Text();
        transactionMessage.getStyleClass().add(
                "message"
        );

        panel.getChildren().addAll(
                title,
                pinPasswordField,
                keypad,
                actions,
                transactionMessage
        );

        return panel;
    }

    private GridPane createPinKeypad() {
        GridPane keypad = new GridPane();
        keypad.setHgap(8);
        keypad.setVgap(8);
        keypad.setAlignment(Pos.CENTER);

        String[] digits = {
                "1", "2", "3",
                "4", "5", "6",
                "7", "8", "9",
                "C", "0", "⌫"
        };

        for (int i = 0; i < digits.length; i++) {
            String digit = digits[i];

            Button button = new Button(digit);
            button.setPrefSize(58, 48);
            button.getStyleClass().add("keypad-button");

            if (digit.equals("C")) {
                button.getStyleClass().add(
                        "keypad-clear"
                );
                button.setOnAction(
                        e -> clearPin()
                );

            } else if (digit.equals("⌫")) {
                button.getStyleClass().add(
                        "keypad-delete"
                );
                button.setOnAction(
                        e -> backspacePin()
                );

            } else {
                button.setOnAction(
                        e -> appendPin(digit)
                );
            }

            keypad.add(
                    button,
                    i % 3,
                    i / 3
            );
        }

        return keypad;
    }

    private VBox createMenuPanel() {
        VBox panel = new VBox(14);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(18));
        panel.getStyleClass().add("panel");

        Label title = new Label("Main Menu");
        title.getStyleClass().add("panel-title");

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.setAlignment(Pos.CENTER);

        Button withdraw = new Button("💵 Withdraw");
        withdraw.getStyleClass().add(
                "menu-button"
        );
        withdraw.setOnAction(
                e -> showTransaction("withdraw")
        );

        Button deposit = new Button("💳 Deposit");
        deposit.getStyleClass().add(
                "menu-button"
        );
        deposit.setOnAction(
                e -> showTransaction("deposit")
        );

        Button balance = new Button("◉ Balance");
        balance.getStyleClass().add(
                "menu-button"
        );
        balance.setOnAction(
                e -> showBalance()
        );

        Button logout = new Button("↪ Logout");
        logout.getStyleClass().add(
                "logout-button"
        );
        logout.setOnAction(
                e -> logout()
        );

        grid.add(withdraw, 0, 0);
        grid.add(deposit, 1, 0);
        grid.add(balance, 0, 1);
        grid.add(logout, 1, 1);

        panel.getChildren().addAll(
                title,
                grid
        );

        return panel;
    }

    private VBox createTransactionPanel() {
        VBox panel = new VBox(14);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(18));
        panel.getStyleClass().add(
                "transaction-panel"
        );

        Label title = new Label("Transaction");
        title.getStyleClass().add(
                "panel-title"
        );

        transactionMessage = new Text();
        transactionMessage.getStyleClass().add(
                "message"
        );

        amountField = new TextField();
        amountField.setPromptText("Enter amount");
        amountField.setPrefWidth(220);
        amountField.getStyleClass().add(
                "amount-field"
        );

        amountMessage = new Text();
        amountMessage.getStyleClass().add(
                "error-message"
        );

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);

        Button confirm = new Button("✓ Confirm");
        confirm.getStyleClass().add(
                "success-button"
        );
        confirm.setOnAction(
                e -> confirmTransaction()
        );

        Button cancel = new Button("✕ Cancel");
        cancel.getStyleClass().add(
                "danger-button"
        );
        cancel.setOnAction(
                e -> cancelTransaction()
        );

        buttons.getChildren().addAll(
                confirm,
                cancel
        );

        panel.getChildren().addAll(
                title,
                transactionMessage,
                amountField,
                amountMessage,
                buttons
        );

        return panel;
    }

    private void appendPin(String digit) {
        String pin = pinPasswordField.getText();

        if (pin.length() < 4) {
            pinPasswordField.setText(
                    pin + digit
            );
        }
    }

    private void backspacePin() {
        String pin = pinPasswordField.getText();

        if (!pin.isEmpty()) {
            pinPasswordField.setText(
                    pin.substring(
                            0,
                            pin.length() - 1
                    )
            );
        }
    }

    private void clearPin() {
        pinPasswordField.clear();

        if (transactionMessage != null) {
            transactionMessage.setText("");
        }
    }

    private void attemptLogin() {
        String pin = pinPasswordField.getText();

        if (pin.isEmpty()) {
            showMessage(
                    "Please enter your PIN.",
                    "error"
            );
            return;
        }

        if (pin.length() != 4) {
            showMessage(
                    "PIN must contain exactly 4 digits.",
                    "error"
            );
            return;
        }

        isLoggedIn = true;

        pinPanel.setVisible(false);
        pinPanel.setManaged(false);

        menuPanel.setVisible(true);
        menuPanel.setManaged(true);

        showMessage(
                "✓ Login successful!",
                "success"
        );

        updateBalanceDisplay();
    }

    private void logout() {
        isLoggedIn = false;

        menuPanel.setVisible(false);
        menuPanel.setManaged(false);

        transactionPanel.setVisible(false);
        transactionPanel.setManaged(false);

        pinPanel.setVisible(true);
        pinPanel.setManaged(true);

        clearPin();

        maskedBalanceText.setVisible(false);
        maskedBalanceText.setManaged(false);

        balanceText.setVisible(true);
        balanceText.setManaged(true);

        currentTransaction = "";
    }

    private void showTransaction(String type) {
        currentTransaction = type;

        menuPanel.setVisible(false);
        menuPanel.setManaged(false);

        transactionPanel.setVisible(true);
        transactionPanel.setManaged(true);

        amountField.clear();
        amountMessage.setText("");

        String message;

        if (type.equals("withdraw")) {
            message = "💵 Enter amount to withdraw";
        } else {
            message = "💳 Enter amount to deposit";
        }

        showTransactionMessage(
                message,
                "normal"
        );
    }

    private void confirmTransaction() {
        String amountText = amountField.getText().trim();

        if (amountText.isEmpty()) {
            showTransactionMessage(
                    "Please enter an amount.",
                    "error"
            );
            return;
        }

        try {
            double amount = Double.parseDouble(
                    amountText
            );

            if (amount <= 0) {
                showTransactionMessage(
                        "Amount must be greater than 0.",
                        "error"
                );
                return;
            }

            String result;

            if (currentTransaction.equals("withdraw")) {
                result = atm.withdraw(amount);
            } else {
                result = atm.deposit(amount);
            }

            boolean successful =
                    result.startsWith("✅");

            showTransactionMessage(
                    result,
                    successful ? "success" : "error"
            );

            if (successful) {
                updateBalanceDisplay();

                PauseTransition pause =
                        new PauseTransition(
                                Duration.seconds(2)
                        );

                pause.setOnFinished(
                        e -> cancelTransaction()
                );

                pause.play();
            }

        } catch (NumberFormatException e) {
            showTransactionMessage(
                    "Please enter a valid amount.",
                    "error"
            );
        }
    }

    private void cancelTransaction() {
        transactionPanel.setVisible(false);
        transactionPanel.setManaged(false);

        menuPanel.setVisible(true);
        menuPanel.setManaged(true);

        amountField.clear();
        currentTransaction = "";
    }

    private void showBalance() {
        String balance = atm.checkBalance();

        showTransactionMessage(
                "💰 " + balance,
                "info"
        );

        PauseTransition pause =
                new PauseTransition(
                        Duration.seconds(3)
                );

        pause.setOnFinished(
                e -> transactionMessage.setText("")
        );

        pause.play();
    }

    private void showTransactionMessage(
            String message,
            String type
    ) {
        transactionMessage.setText(message);

        transactionMessage.getStyleClass().removeAll(
                "message-success",
                "message-error",
                "message-info"
        );

        switch (type) {
            case "success":
                    transactionMessage
                            .getStyleClass()
                            .add("message-success");

            case "error":
                    transactionMessage
                            .getStyleClass()
                            .add("message-error");

            case "info":
                    transactionMessage
                            .getStyleClass()
                            .add("message-info");
        }
    }

    private void showMessage(
            String message,
            String type
    ) {
        showTransactionMessage(
                message,
                type
        );
    }

    private void toggleBalanceVisibility() {
        if (!isLoggedIn) {
            return;
        }

        boolean hidden =
                maskedBalanceText.isVisible();

        maskedBalanceText.setVisible(!hidden);
        maskedBalanceText.setManaged(!hidden);

        balanceText.setVisible(hidden);
        balanceText.setManaged(hidden);
    }

    private void updateBalanceDisplay() {
        double balance = userAccount.getBalance();

        balanceText.setText(
                currencyFormat.format(balance)
        );

        maskedBalanceText.setText("••••••");

        if (!isLoggedIn) {
            balanceText.setVisible(true);
            balanceText.setManaged(true);

            maskedBalanceText.setVisible(false);
            maskedBalanceText.setManaged(false);
        }
    }
}