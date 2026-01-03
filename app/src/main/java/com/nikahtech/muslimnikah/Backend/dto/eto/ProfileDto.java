package com.nikahtech.muslimnikah.Backend.dto.eto;

import com.nikahtech.muslimnikah.Backend.enums.Gender;
import com.nikahtech.muslimnikah.Backend.enums.PhotoPrivacy;

import java.time.LocalDate;
import java.util.List;

public class ProfileDto {
    /* META */
    private Long id;
    private Long userId;
    private Integer profileBoost;
    private String profileCreatedBy;

    /* BASIC */
    private String name;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String maritalStatus;
    private String disabilityStatus;
    private Integer height;

    /* CONTACT */
    private String phoneNumber;
    private String emailAddress;

    /* PHOTOS */
    private String profilePic;
    private List<String> gallery;
    private PhotoPrivacy profilePhotoPrivacy;
    private PhotoPrivacy galleryPhotosPrivacy;

    /* LOCATION */
    private String country;
    private String state;
    private String city;
    private String motherTongue;

    /* RELIGION */
    private String sect;
    private String caste;
    private String prayerHabit;
    private String quranReading;
    private String halalDiet;
    private String beardHijab;
    private String involvement;

    /* EDUCATION */
    private String highestEducation;
    private String fieldOfStudy;
    private String occupation;
    private String employmentType;
    private String incomeRange;

    /* FAMILY */
    private String familyType;
    private String familyStatus;
    private String fatherOccupation;
    private String motherOccupation;
    private String siblingsCount;

    public ProfileDto() {
    }

    public ProfileDto(Long id, Long userId, String profileCreatedBy, String name, Gender gender, LocalDate dateOfBirth, String maritalStatus, String disabilityStatus, Integer height, String phoneNumber, String emailAddress, String profilePic, PhotoPrivacy profilePhotoPrivacy, PhotoPrivacy galleryPhotosPrivacy, String country, String state, String city, String motherTongue, String sect, String caste, String prayerHabit, String quranReading, String halalDiet, String beardHijab, String involvement, String highestEducation, String fieldOfStudy, String occupation, String employmentType, String incomeRange, String familyType, String familyStatus, String fatherOccupation, String motherOccupation, String siblingsCount) {
        this.id = id;
        this.userId = userId;
        this.profileCreatedBy = profileCreatedBy;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.maritalStatus = maritalStatus;
        this.disabilityStatus = disabilityStatus;
        this.height = height;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.profilePic = profilePic;
        this.profilePhotoPrivacy = profilePhotoPrivacy;
        this.galleryPhotosPrivacy = galleryPhotosPrivacy;
        this.country = country;
        this.state = state;
        this.city = city;
        this.motherTongue = motherTongue;
        this.sect = sect;
        this.caste = caste;
        this.prayerHabit = prayerHabit;
        this.quranReading = quranReading;
        this.halalDiet = halalDiet;
        this.beardHijab = beardHijab;
        this.involvement = involvement;
        this.highestEducation = highestEducation;
        this.fieldOfStudy = fieldOfStudy;
        this.occupation = occupation;
        this.employmentType = employmentType;
        this.incomeRange = incomeRange;
        this.familyType = familyType;
        this.familyStatus = familyStatus;
        this.fatherOccupation = fatherOccupation;
        this.motherOccupation = motherOccupation;
        this.siblingsCount = siblingsCount;
    }

    @Override
    public String toString() {
        return "ProfileDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", profileCreatedBy='" + profileCreatedBy + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", dateOfBirth=" + dateOfBirth +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", disabilityStatus='" + disabilityStatus + '\'' +
                ", height=" + height +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", profilePhotoPrivacy=" + profilePhotoPrivacy +
                ", galleryPhotosPrivacy=" + galleryPhotosPrivacy +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", motherTongue='" + motherTongue + '\'' +
                ", sect='" + sect + '\'' +
                ", caste='" + caste + '\'' +
                ", prayerHabit='" + prayerHabit + '\'' +
                ", quranReading='" + quranReading + '\'' +
                ", halalDiet='" + halalDiet + '\'' +
                ", beardHijab='" + beardHijab + '\'' +
                ", involvement='" + involvement + '\'' +
                ", highestEducation='" + highestEducation + '\'' +
                ", fieldOfStudy='" + fieldOfStudy + '\'' +
                ", occupation='" + occupation + '\'' +
                ", employmentType='" + employmentType + '\'' +
                ", incomeRange='" + incomeRange + '\'' +
                ", familyType='" + familyType + '\'' +
                ", familyStatus='" + familyStatus + '\'' +
                ", fatherOccupation='" + fatherOccupation + '\'' +
                ", motherOccupation='" + motherOccupation + '\'' +
                ", siblingsCount='" + siblingsCount + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getProfileBoost() {
        return profileBoost;
    }

    public void setProfileBoost(Integer profileBoost) {
        this.profileBoost = profileBoost;
    }

    public String getProfileCreatedBy() {
        return profileCreatedBy;
    }

    public void setProfileCreatedBy(String profileCreatedBy) {
        this.profileCreatedBy = profileCreatedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getDisabilityStatus() {
        return disabilityStatus;
    }

    public void setDisabilityStatus(String disabilityStatus) {
        this.disabilityStatus = disabilityStatus;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(List<String> gallery) {
        this.gallery = gallery;
    }

    public PhotoPrivacy getProfilePhotoPrivacy() {
        return profilePhotoPrivacy;
    }

    public void setProfilePhotoPrivacy(PhotoPrivacy profilePhotoPrivacy) {
        this.profilePhotoPrivacy = profilePhotoPrivacy;
    }

    public PhotoPrivacy getGalleryPhotosPrivacy() {
        return galleryPhotosPrivacy;
    }

    public void setGalleryPhotosPrivacy(PhotoPrivacy galleryPhotosPrivacy) {
        this.galleryPhotosPrivacy = galleryPhotosPrivacy;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMotherTongue() {
        return motherTongue;
    }

    public void setMotherTongue(String motherTongue) {
        this.motherTongue = motherTongue;
    }

    public String getSect() {
        return sect;
    }

    public void setSect(String sect) {
        this.sect = sect;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getPrayerHabit() {
        return prayerHabit;
    }

    public void setPrayerHabit(String prayerHabit) {
        this.prayerHabit = prayerHabit;
    }

    public String getQuranReading() {
        return quranReading;
    }

    public void setQuranReading(String quranReading) {
        this.quranReading = quranReading;
    }

    public String getHalalDiet() {
        return halalDiet;
    }

    public void setHalalDiet(String halalDiet) {
        this.halalDiet = halalDiet;
    }

    public String getBeardHijab() {
        return beardHijab;
    }

    public void setBeardHijab(String beardHijab) {
        this.beardHijab = beardHijab;
    }

    public String getInvolvement() {
        return involvement;
    }

    public void setInvolvement(String involvement) {
        this.involvement = involvement;
    }

    public String getHighestEducation() {
        return highestEducation;
    }

    public void setHighestEducation(String highestEducation) {
        this.highestEducation = highestEducation;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getIncomeRange() {
        return incomeRange;
    }

    public void setIncomeRange(String incomeRange) {
        this.incomeRange = incomeRange;
    }

    public String getFamilyType() {
        return familyType;
    }

    public void setFamilyType(String familyType) {
        this.familyType = familyType;
    }

    public String getFamilyStatus() {
        return familyStatus;
    }

    public void setFamilyStatus(String familyStatus) {
        this.familyStatus = familyStatus;
    }

    public String getFatherOccupation() {
        return fatherOccupation;
    }

    public void setFatherOccupation(String fatherOccupation) {
        this.fatherOccupation = fatherOccupation;
    }

    public String getMotherOccupation() {
        return motherOccupation;
    }

    public void setMotherOccupation(String motherOccupation) {
        this.motherOccupation = motherOccupation;
    }

    public String getSiblingsCount() {
        return siblingsCount;
    }

    public void setSiblingsCount(String siblingsCount) {
        this.siblingsCount = siblingsCount;
    }
}
