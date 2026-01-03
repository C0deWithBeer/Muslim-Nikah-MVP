package com.nikahtech.muslimnikah.models;

import com.nikahtech.muslimnikah.Backend.enums.Gender;
import com.nikahtech.muslimnikah.Backend.enums.PhotoPrivacy;

import java.time.LocalDate;
import java.util.List;

public class Profile {

    private Long id;
    private Long userId;
    private String mid;
    private Integer profileBoost;

    private String profilePic;
    private List<String> gallery;

    private PhotoPrivacy profilePhotoPrivacy;
    private PhotoPrivacy galleryPhotosPrivacy;

    private String profileCreatedBy;
    private String name;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String maritalStatus;
    private String disabilityStatus;
    private Integer height;

    private String phoneNumber;
    private String emailAddress;

    private String country;
    private String state;
    private String city;
    private String motherTongue;

    private String sect;
    private String caste;
    private String prayerHabit;
    private String quranReading;
    private String halalDiet;
    private String beardHijab;
    private String involvement;

    private String highestEducation;
    private String fieldOfStudy;
    private String occupation;
    private String employmentType;
    private String incomeRange;

    private String familyType;
    private String familyStatus;
    private String fatherOccupation;
    private String motherOccupation;
    private String siblingsCount;

    public Profile() {
    }

    public Profile(String profilePic, String name) {
        this.profilePic = profilePic;
        this.name = name;
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

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public Integer getProfileBoost() {
        return profileBoost;
    }

    public void setProfileBoost(Integer profileBoost) {
        this.profileBoost = profileBoost;
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


    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", userId=" + userId +
                ", mid='" + mid + '\'' +
                ", profileBoost=" + profileBoost +
                ", profilePic='" + profilePic + '\'' +
                ", gallery=" + gallery +
                ", profilePhotoPrivacy=" + profilePhotoPrivacy +
                ", galleryPhotosPrivacy=" + galleryPhotosPrivacy +
                ", profileCreatedBy='" + profileCreatedBy + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", dateOfBirth=" + dateOfBirth +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", disabilityStatus='" + disabilityStatus + '\'' +
                ", height=" + height +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
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
}
