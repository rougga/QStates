package ma.rougga.qstates.modal;

public class Cible {

    private String serviceId;
    private String serviceName;
    private long cibleA;
    private long cibleT;
    private double ciblePer;

    // Default constructor
    public Cible() {
    }

    // Parameterized constructor
    public Cible(String serviceId, String serviceName, long cibleA, long cibleT, double ciblePer) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.cibleA = cibleA;
        this.cibleT = cibleT;
        this.ciblePer = ciblePer;
    }

    // Getters
    public String getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public long getCibleA() {
        return cibleA;
    }

    public long getCibleT() {
        return cibleT;
    }

    public double getCiblePer() {
        return ciblePer;
    }

    // Setters
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setCibleA(long cibleA) {
        this.cibleA = cibleA;
    }

    public void setCibleT(long cibleT) {
        this.cibleT = cibleT;
    }

    public void setCiblePer(double ciblePer) {
        this.ciblePer = ciblePer;
    }

    // toString method
    @Override
    public String toString() {
        return "Cible{"
                + "serviceId='" + serviceId + '\''
                + ", serviceName='" + serviceName + '\''
                + ", cibleA=" + cibleA
                + ", cibleT=" + cibleT
                + ", ciblePer=" + ciblePer
                + '}';
    }
}
