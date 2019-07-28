package br.com.alexandre.auth.domain;

import java.io.Serializable;

public class Company implements Serializable {

  private static final long serialVersionUID = 6538887171370472032L;

  private Long companyId;
  private String fantasyName;
  private String socialName;
  private String acronym;
  private String address;
  private String neighborhood;
  private String city;
  private String state;
  private String zipCode;
  private String phoneDdd;
  private String phoneNumber;
  private String faxDdd;
  private String faxNumber;
  private String cgcNumber;
  private String cgcDigit;

  public Company() {}

  public Long getCompanyId() {
    return companyId;
  }

  public void setCompanyId(Long companyId) {
    this.companyId = companyId;
  }

  public String getFantasyName() {
    return fantasyName;
  }

  public void setFantasyName(String fantasyName) {
    this.fantasyName = fantasyName;
  }

  public String getSocialName() {
    return socialName;
  }

  public void setSocialName(String socialName) {
    this.socialName = socialName;
  }

  public String getAcronym() {
    return acronym;
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getNeighborhood() {
    return neighborhood;
  }

  public void setNeighborhood(String neighborhood) {
    this.neighborhood = neighborhood;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getPhoneDdd() {
    return phoneDdd;
  }

  public void setPhoneDdd(String phoneDdd) {
    this.phoneDdd = phoneDdd;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getFaxDdd() {
    return faxDdd;
  }

  public void setFaxDdd(String faxDdd) {
    this.faxDdd = faxDdd;
  }

  public String getFaxNumber() {
    return faxNumber;
  }

  public void setFaxNumber(String faxNumber) {
    this.faxNumber = faxNumber;
  }

  public String getCgcNumber() {
    return cgcNumber;
  }

  public void setCgcNumber(String cgcNumber) {
    this.cgcNumber = cgcNumber;
  }

  public String getCgcDigit() {
    return cgcDigit;
  }

  public void setCgcDigit(String cgcDigit) {
    this.cgcDigit = cgcDigit;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Company other = (Company) obj;
    if (companyId == null) {
      if (other.companyId != null) {
        return false;
      }
    } else if (!companyId.equals(other.companyId)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Company [companyId="
        + companyId
        + ", fantasyName="
        + fantasyName
        + ", socialName="
        + socialName
        + ", acronym="
        + acronym
        + ", address="
        + address
        + ", neighborhood="
        + neighborhood
        + ", city="
        + city
        + ", state="
        + state
        + ", zipCode="
        + zipCode
        + ", phoneDdd="
        + phoneDdd
        + ", phoneNumber="
        + phoneNumber
        + ", faxDdd="
        + faxDdd
        + ", faxNumber="
        + faxNumber
        + ", cgcNumber="
        + cgcNumber
        + ", cgcDigit="
        + cgcDigit
        + "]";
  }
}
