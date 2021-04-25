package br.com.alexandre.auth.domain;

import java.io.Serializable;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.EqualsAndHashCode(of = { "companyId" })
@lombok.ToString
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

}
