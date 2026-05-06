public abstract class ShipmentOrder implements SummaryPrintable {

    protected String orderId;
    protected String customerName;
    protected double distanceKm;
    protected double baseFee;
    protected boolean insured;

    protected double lastCalculatedPrice;

    public ShipmentOrder(String orderId, String customerName, double distanceKm, double baseFee, boolean insured) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.distanceKm = distanceKm;
        this.baseFee = baseFee;
        this.insured = insured;
    }

    public final void processOrder() {
        validateOrder();
        validateSpecificRules();

        double price = calculateBasePrice();
        price += calculateAdditionalFee();

        price = applyInsurance(price);
        price = applyBusinessDiscount(price);

        lastCalculatedPrice = price;
        printProcessingResult();
    }


    private void validateOrder() {
        if (orderId == null || orderId.isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be empty");
        }
        if (customerName == null || customerName.isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }
        if (distanceKm <= 0) {
            throw new IllegalArgumentException("Distance must be positive");
        }
    }

    protected void validateSpecificRules() {
    }

    private double applyInsurance(double price) {
        if (insured) {
            return price * 1.07;
        }
        return price;
    }

    protected double applyBusinessDiscount(double price) {
        return price;
    }

    private void printProcessingResult() {
        System.out.println("Processed order: " + orderId);
    }

    @Override
    public String buildSummaryLine() {
        return "Order: " + orderId +
                ", Customer: " + customerName +
                ", Type: " + getShipmentType() +
                ", Price: " + String.format("%.2f", lastCalculatedPrice) + " PLN";
    }


    protected abstract double calculateBasePrice();
    protected abstract double calculateAdditionalFee();
    public abstract String getShipmentType();
}