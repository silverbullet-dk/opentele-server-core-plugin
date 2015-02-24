
package org.opentele.server.dgks.monitoringdataset.version1_0_1.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AddressPostalType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AddressPostalType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/}MailDeliverySublocationIdentifier" minOccurs="0"/>
 *         &lt;element ref="{http://rep.oio.dk/ebxml/xml/schemas/dkcc/2005/03/15/}StreetName"/>
 *         &lt;element ref="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}StreetNameForAddressingName" minOccurs="0"/>
 *         &lt;element ref="{http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/}StreetBuildingIdentifier"/>
 *         &lt;element ref="{http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/}FloorIdentifier" minOccurs="0"/>
 *         &lt;element ref="{http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/}SuiteIdentifier" minOccurs="0"/>
 *         &lt;element ref="{http://rep.oio.dk/ebxml/xml/schemas/dkcc/2005/03/15/}DistrictSubdivisionIdentifier" minOccurs="0"/>
 *         &lt;element ref="{http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/}PostOfficeBoxIdentifier" minOccurs="0"/>
 *         &lt;element ref="{http://rep.oio.dk/ebxml/xml/schemas/dkcc/2005/03/15/}PostCodeIdentifier"/>
 *         &lt;element ref="{http://rep.oio.dk/ebxml/xml/schemas/dkcc/2005/03/15/}DistrictName"/>
 *         &lt;element ref="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}MunicipalityName" minOccurs="0"/>
 *         &lt;element ref="{http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/}CountryIdentificationCode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressPostalType", namespace = "http://rep.oio.dk/xkom.dk/xml/schemas/2005/03/15/", propOrder = {
    "mailDeliverySublocationIdentifier",
    "streetName",
    "streetNameForAddressingName",
    "streetBuildingIdentifier",
    "floorIdentifier",
    "suiteIdentifier",
    "districtSubdivisionIdentifier",
    "postOfficeBoxIdentifier",
    "postCodeIdentifier",
    "districtName",
    "municipalityName",
    "countryIdentificationCode"
})
public class AddressPostalType {

    @XmlElement(name = "MailDeliverySublocationIdentifier", namespace = "http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/")
    protected String mailDeliverySublocationIdentifier;
    @XmlElement(name = "StreetName", namespace = "http://rep.oio.dk/ebxml/xml/schemas/dkcc/2005/03/15/", required = true)
    protected String streetName;
    @XmlElement(name = "StreetNameForAddressingName", namespace = "http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/")
    protected String streetNameForAddressingName;
    @XmlElement(name = "StreetBuildingIdentifier", namespace = "http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/", required = true)
    protected String streetBuildingIdentifier;
    @XmlElement(name = "FloorIdentifier", namespace = "http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/")
    protected String floorIdentifier;
    @XmlElement(name = "SuiteIdentifier", namespace = "http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/")
    protected String suiteIdentifier;
    @XmlElement(name = "DistrictSubdivisionIdentifier", namespace = "http://rep.oio.dk/ebxml/xml/schemas/dkcc/2005/03/15/")
    protected String districtSubdivisionIdentifier;
    @XmlElement(name = "PostOfficeBoxIdentifier", namespace = "http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/")
    protected String postOfficeBoxIdentifier;
    @XmlElement(name = "PostCodeIdentifier", namespace = "http://rep.oio.dk/ebxml/xml/schemas/dkcc/2005/03/15/", required = true)
    protected String postCodeIdentifier;
    @XmlElement(name = "DistrictName", namespace = "http://rep.oio.dk/ebxml/xml/schemas/dkcc/2005/03/15/", required = true)
    protected String districtName;
    @XmlElement(name = "MunicipalityName", namespace = "http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/")
    protected String municipalityName;
    @XmlElement(name = "CountryIdentificationCode", namespace = "http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/")
    protected CountryIdentificationCodeType countryIdentificationCode;

    /**
     * Gets the value of the mailDeliverySublocationIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailDeliverySublocationIdentifier() {
        return mailDeliverySublocationIdentifier;
    }

    /**
     * Sets the value of the mailDeliverySublocationIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailDeliverySublocationIdentifier(String value) {
        this.mailDeliverySublocationIdentifier = value;
    }

    /**
     * Gets the value of the streetName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Sets the value of the streetName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetName(String value) {
        this.streetName = value;
    }

    /**
     * Gets the value of the streetNameForAddressingName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetNameForAddressingName() {
        return streetNameForAddressingName;
    }

    /**
     * Sets the value of the streetNameForAddressingName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetNameForAddressingName(String value) {
        this.streetNameForAddressingName = value;
    }

    /**
     * Gets the value of the streetBuildingIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetBuildingIdentifier() {
        return streetBuildingIdentifier;
    }

    /**
     * Sets the value of the streetBuildingIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetBuildingIdentifier(String value) {
        this.streetBuildingIdentifier = value;
    }

    /**
     * Gets the value of the floorIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFloorIdentifier() {
        return floorIdentifier;
    }

    /**
     * Sets the value of the floorIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFloorIdentifier(String value) {
        this.floorIdentifier = value;
    }

    /**
     * Gets the value of the suiteIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuiteIdentifier() {
        return suiteIdentifier;
    }

    /**
     * Sets the value of the suiteIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuiteIdentifier(String value) {
        this.suiteIdentifier = value;
    }

    /**
     * Gets the value of the districtSubdivisionIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrictSubdivisionIdentifier() {
        return districtSubdivisionIdentifier;
    }

    /**
     * Sets the value of the districtSubdivisionIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrictSubdivisionIdentifier(String value) {
        this.districtSubdivisionIdentifier = value;
    }

    /**
     * Gets the value of the postOfficeBoxIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostOfficeBoxIdentifier() {
        return postOfficeBoxIdentifier;
    }

    /**
     * Sets the value of the postOfficeBoxIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostOfficeBoxIdentifier(String value) {
        this.postOfficeBoxIdentifier = value;
    }

    /**
     * Gets the value of the postCodeIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostCodeIdentifier() {
        return postCodeIdentifier;
    }

    /**
     * Sets the value of the postCodeIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostCodeIdentifier(String value) {
        this.postCodeIdentifier = value;
    }

    /**
     * Gets the value of the districtName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrictName() {
        return districtName;
    }

    /**
     * Sets the value of the districtName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrictName(String value) {
        this.districtName = value;
    }

    /**
     * Gets the value of the municipalityName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMunicipalityName() {
        return municipalityName;
    }

    /**
     * Sets the value of the municipalityName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMunicipalityName(String value) {
        this.municipalityName = value;
    }

    /**
     * Gets the value of the countryIdentificationCode property.
     * 
     * @return
     *     possible object is
     *     {@link CountryIdentificationCodeType }
     *     
     */
    public CountryIdentificationCodeType getCountryIdentificationCode() {
        return countryIdentificationCode;
    }

    /**
     * Sets the value of the countryIdentificationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CountryIdentificationCodeType }
     *     
     */
    public void setCountryIdentificationCode(CountryIdentificationCodeType value) {
        this.countryIdentificationCode = value;
    }

}
