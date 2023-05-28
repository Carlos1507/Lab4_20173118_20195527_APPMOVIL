package ayala.carlos.lab4_20173118_20195527.entities;


public class Countries {
    private String countryId;
    private String countryName;
    private Regions regionsId;

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Regions getRegionId() {
        return regionsId;
    }

    public void setRegionId(Regions regionsId) {
        this.regionsId = regionsId;
    }
}
