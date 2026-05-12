public class PickupPointShipment extends ShipmentOrder {

    private String lockerSize;
    private boolean fragile;

    public PickupPointShipment(String orderId, String customerName,
                               double distanceKm, double baseFee, boolean insured,
                               String lockerSize, boolean fragile) {
        super(orderId, customerName, distanceKm, baseFee, insured);
        this.lockerSize = lockerSize;
        this.fragile = fragile;
    }

    @Override
    public String getShipmentType() {
        return "Pickup point";
    }

    @Override
    protected double calculateBasePrice() {
        return baseFee + distanceKm * 0.75;
    }

    @Override
    protected double calculateAdditionalFee() {
        double fee = 0;

        switch (lockerSize) {
            case "S": fee += 5; break;
            case "M": fee += 10; break;
            case "L": fee += 18; break;
        }

        if (fragile) {
            fee += 12;
        }

        return fee;
    }

    @Override
    protected void validateSpecificRules() {
        if (!lockerSize.equals("S") && !lockerSize.equals("M") && !lockerSize.equals("L")) {
            throw new IllegalArgumentException("Invalid locker size");
        }
    }
}