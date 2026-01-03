package com.nikahtech.muslimnikah.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ProfileOptionsManager {

    private ProfileOptionsManager() {
    }

    // --------------------------------------------------
    // BASIC INFO
    // --------------------------------------------------

    public static List<String> profileCreatedBy() {
        return List.of(
                "Self",
                "Father",
                "Mother",
                "Brother",
                "Sister",
                "Son",
                "Daughter",
                "Uncle",
                "Aunt",
                "Grandfather",
                "Grandmother",
                "Guardian",
                "Relative",
                "Friend",
                "Broker/Agent"
        );
    }

    public static List<String> gender() {
        return List.of(
                "Male",
                "Female"
        );
    }

    public static List<String> heightInFt() {
        List<String> heights = new ArrayList<>();

        for (int ft = 4; ft <= 6; ft++) {
            for (int inch = 0; inch <= 11; inch++) {

                if (ft == 6 && inch > 9) break;

                if (inch == 0) {
                    heights.add(ft + " ft");
                } else {
                    heights.add(ft + " ft " + inch + " in");
                }
            }
        }

        return heights;
    }

    public static List<String> maritalStatus() {
        return List.of(
                "Never Married",
                "Divorced",
                "Awaiting Divorce",
                "Widowed",
                "Separated",
                "Annulled"
        );
    }

    public static List<String> disabilityStatus() {
        return List.of(
                "None",
                "Physically Challenged",
                "Visually Impaired",
                "Hearing Impaired",
                "Speech Impaired",
                "Intellectual Disability",
                "Chronic Illness",
                "Other"
        );
    }

    // --------------------------------------------------
    // EDU & WORK
    // --------------------------------------------------

    public static List<String> highestEducation() {
        return List.of(
                "Doctorate (Ph.D./Professional)",
                "Post Graduate (Masters/M.S./M.D.)",
                "Under Graduate (Bachelors/B.E./B.Sc.)",
                "Higher Secondary (12th/A-Levels)",
                "Secondary (10th/GCSE)",
                "Diploma (Polytechnic/Vocational)",
                "Professional Certification (CA/CS/CFA)",
                "Islamic/Religious Education (Alim/Hafiz)",
                "Below 10th Standard",
                "Other"
        );
    }

    public static List<String> fieldOfStudy() {
        return List.of(
                // --- ENGINEERING & TECHNOLOGY ---
                "Computer Science Engineering",
                "Software Engineering",
                "Information Technology",
                "Artificial Intelligence",
                "Machine Learning",
                "Deep Learning",
                "Data Science",
                "Big Data Engineering",
                "Cyber Security",
                "Ethical Hacking",
                "Cloud Computing",
                "DevOps Engineering",
                "Site Reliability Engineering",
                "Blockchain Technology",
                "Internet of Things (IoT)",
                "Edge Computing",
                "Computer Networks",
                "Computer Engineering",
                "Mechanical Engineering",
                "Automotive Design Engineering",
                "Civil Engineering",
                "Structural Design Engineering",
                "Electrical Engineering",
                "Power Engineering",
                "Electronics & Communication",
                "Microelectronics",
                "Chemical Engineering",
                "Process Engineering",
                "Aerospace Engineering",
                "Aeronautical Engineering",
                "Automobile Engineering",
                "Biotechnology Engineering",
                "Biomedical Engineering",
                "Mechatronics",
                "Robotics",
                "Marine Engineering",
                "Naval Architecture",
                "Petroleum Engineering",
                "Mining Engineering",
                "Metallurgical Engineering",
                "Textile Technology",
                "Production Engineering",
                "Industrial Engineering",
                "Instrumentation Engineering",
                "Environmental Engineering",
                "Nanotechnology",
                "Materials Engineering",
                "Agricultural Engineering",
                "Food Technology",
                "Food Processing Engineering",
                "Nuclear Engineering",
                "Safety & Fire Engineering",
                "Systems Engineering",
                "Reliability Engineering",
                "Structural Engineering",
                "Geotechnical Engineering",
                "Water Resources Engineering",
                "Transportation Engineering",
                "Construction Engineering",
                "Urban Infrastructure Engineering",
                "Ceramic Engineering",
                "Polymer Technology",
                "Manufacturing Engineering",
                "Automated Manufacturing",
                "Thermal Engineering",
                "Power Systems Engineering",
                "Renewable Energy Engineering",
                "VLSI Design",
                "Embedded Systems",
                "Signal Processing",
                "Control Systems",
                "Network Engineering",
                "Telecommunication Engineering",

                // --- MEDICAL & HEALTHCARE ---
                "General Medicine (MBBS)",
                "Internal Medicine",
                "Family Medicine",
                "Cardiology",
                "Interventional Cardiology",
                "Neurology",
                "Neurosciences",
                "Dermatology",
                "Cosmetology",
                "Pediatrics",
                "Neonatology",
                "Psychiatry",
                "Clinical Psychology",
                "Radiology",
                "Diagnostic Imaging",
                "Oncology",
                "Medical Oncology",
                "Radiation Oncology",
                "Endocrinology",
                "Gastroenterology",
                "Hepatology",
                "Nephrology",
                "General Surgery",
                "Laparoscopic Surgery",
                "Orthopedics",
                "Sports Orthopedics",
                "Ophthalmology",
                "ENT (Otolaryngology)",
                "Obstetrics & Gynaecology",
                "Reproductive Medicine",
                "Anesthesiology",
                "Pain Management",
                "Pathology",
                "Hematology",
                "Dental Surgery (BDS)",
                "Oral & Maxillofacial Surgery",
                "Orthodontics",
                "Periodontics",
                "Prosthodontics",
                "Endodontics",
                "Homeopathy (BHMS)",
                "Ayurveda (BAMS)",
                "Unani Medicine (BUMS)",
                "Veterinary Sciences",
                "Pharmacy (B.Pharm)",
                "Clinical Pharmacy",
                "Pharmacology",
                "Pharmaceutics",
                "Physiotherapy (BPT)",
                "Occupational Therapy",
                "Nursing (B.Sc Nursing)",
                "Critical Care Nursing",
                "Optometry",
                "Medical Lab Technology",
                "Radiology Technology",
                "Speech & Hearing Therapy",
                "Public Health (MPH)",
                "Community Medicine",
                "Nutrition & Dietetics",
                "Biostatistics",
                "Clinical Research",
                "Forensic Medicine",
                "Plastic Surgery",
                "Cosmetic Surgery",
                "Urology",
                "Neuro Surgery",
                "Cardiothoracic Surgery",
                "Microbiology",
                "Immunology",
                "Biochemistry",
                "Epidemiology",
                "Health Administration",
                "Hospital Management",
                "Healthcare Quality Management",
                "Emergency Medicine",
                "Trauma Care",
                "Sports Medicine",

                // --- ISLAMIC & RELIGIOUS STUDIES ---
                "Quranic Studies (Tafsir)",
                "Hadith Studies",
                "Islamic Jurisprudence (Fiqh)",
                "Principles of Fiqh (Usul al-Fiqh)",
                "Islamic Theology (Aqeedah)",
                "Islamic History",
                "Arabic Language & Literature",
                "Classical Arabic Studies",
                "Islamic Finance & Banking",
                "Sharia Compliance Studies",
                "Sharia Law",
                "Dawah & Comparative Religion",
                "Comparative Theology",
                "Tajweed & Qira'at",
                "Islamic Philosophy",
                "Sufi Studies (Tasawwuf)",
                "Islamic Ethics",
                "Islamic Arts & Architecture",
                "Islamic Calligraphy",
                "Seerah (Prophetic Biography)",
                "Islamic Economics",
                "Hifz-ul-Quran",
                "Quran Memorization & Ijazah",
                "Dars-e-Nizami",
                "Alim Course",
                "Mufti Course",
                "Ifta",
                "Islamic Psychology",
                "Islamic Counseling",
                "Ethics (Akhlaq)",

                // --- BUSINESS, MANAGEMENT & FINANCE ---
                "Business Administration (MBA)",
                "General Management",
                "Marketing Management",
                "Digital Marketing Strategy",
                "Finance & Investment",
                "Financial Management",
                "Human Resource Management",
                "Talent Management",
                "Operations Management",
                "Supply Chain & Logistics",
                "Procurement Management",
                "International Business",
                "Global Trade Management",
                "Accounting & Taxation",
                "Chartered Accountancy (CA)",
                "Company Secretary (CS)",
                "Cost & Management Accounting (CMA)",
                "CFA",
                "Investment Banking",
                "Actuarial Science",
                "Banking & Insurance",
                "Risk & Compliance Management",
                "Hospitality Management",
                "Hotel Management",
                "Retail Management",
                "Luxury Brand Management",
                "Entrepreneurship",
                "Startup Management",
                "E-Commerce",
                "Business Analytics",
                "Decision Science",
                "Project Management",
                "Program Management",
                "Risk Management",

                // --- LAW & LEGAL STUDIES ---
                "Corporate Law",
                "Business Law",
                "Criminal Law",
                "Civil Law",
                "International Law",
                "Intellectual Property Law",
                "Patent Law",
                "Human Rights Law",
                "Labor Law",
                "Employment Law",
                "Taxation Law",
                "Cyber Law",
                "IT Law",
                "Environmental Law",
                "Constitutional Law",
                "Maritime Law",
                "Aviation Law",
                "Media & Entertainment Law",
                "Family Law",
                "Islamic Family Law",

                // --- PURE & APPLIED SCIENCES ---
                "Physics",
                "Applied Physics",
                "Chemistry",
                "Analytical Chemistry",
                "Mathematics",
                "Applied Mathematics",
                "Statistics",
                "Data Statistics",
                "Biology",
                "Zoology",
                "Botany",
                "Life Sciences",
                "Biotechnology",
                "Geology",
                "Earth Sciences",
                "Microbiology",
                "Genetics",
                "Molecular Biology",
                "Astronomy & Astrophysics",
                "Environmental Science",
                "Climate Science",
                "Oceanography",
                "Marine Science",
                "Forensic Science",
                "Material Science",
                "Polymer Science",
                "Organic Chemistry",
                "Inorganic Chemistry",
                "Quantum Physics",

                // --- ARTS, HUMANITIES & SOCIAL SCIENCES ---
                "English Literature",
                "Comparative Literature",
                "Fine Arts",
                "Visual Arts",
                "Graphic Design",
                "UI/UX Design",
                "Interior Design",
                "Fashion Design",
                "Textile Design",
                "Product Design",
                "Industrial Design",
                "Animation & Multimedia",
                "Game Design",
                "Psychology",
                "Applied Psychology",
                "Sociology",
                "Philosophy",
                "History",
                "World History",
                "Political Science",
                "International Relations",
                "Geography",
                "Human Geography",
                "Economics",
                "Development Economics",
                "Social Work (MSW)",
                "Community Development",
                "Archaeology",
                "Anthropology",
                "Cultural Studies",
                "Linguistics",
                "Foreign Languages",
                "Translation Studies",

                // --- EDUCATION & TRAINING ---
                "Early Childhood Education",
                "Primary Education",
                "Secondary Education",
                "Special Education",
                "Inclusive Education",
                "Physical Education",
                "Education Leadership",
                "Educational Management",
                "Curriculum Design",
                "Instructional Design",
                "Instructional Technology",
                "Educational Technology",
                "Adult Education",
                "School Administration",
                "Higher Education Administration",

                // --- VOCATIONAL & SKILL-BASED ---
                "Culinary Arts (Chef)",
                "Bakery & Pastry Arts",
                "Automobile Repair",
                "Automobile Diagnostics",
                "Electrician",
                "Industrial Electrician",
                "Carpentry",
                "Furniture Design",
                "Plumbing",
                "Pipe Fitting",
                "HVAC Technician",
                "Refrigeration Technology",
                "Welding",
                "Fabrication Technology",
                "Heavy Machinery Operation",
                "Construction Equipment Operation",
                "Beauty & Cosmetology",
                "Hair & Makeup Artistry",
                "Photography",
                "Commercial Photography",
                "Videography",
                "Cinematography",
                "Digital Marketing",
                "Performance Marketing",
                "SEO Specialist",
                "Content Marketing",

                // --- AVIATION & TRANSPORT ---
                "Commercial Pilot License (CPL)",
                "Airline Transport Pilot License (ATPL)",
                "Aircraft Maintenance Engineering",
                "Avionics Engineering",
                "Airport Management",
                "Aviation Management",
                "Cabin Crew / Flight Attendant Training",
                "Air Traffic Control",
                "Aviation Safety Management",
                "Logistics & Shipping",
                "Supply Chain Transportation",

                // --- AGRICULTURE & ALLIED ---
                "Agronomy",
                "Crop Science",
                "Horticulture",
                "Floriculture",
                "Animal Husbandry",
                "Livestock Management",
                "Soil Science",
                "Fisheries Science",
                "Aquaculture",
                "Forestry",
                "Agroforestry",
                "Agri-Business Management",
                "Rural Management",
                "Dairy Technology",
                "Food & Dairy Processing",

                // --- MEDIA, COMMUNICATION & CREATIVE ---
                "Journalism",
                "Print Journalism",
                "Broadcast Journalism",
                "Mass Communication",
                "Media Studies",
                "Public Relations (PR)",
                "Corporate Communication",
                "Advertising",
                "Brand Communication",
                "Film Making",
                "Direction & Screenwriting",
                "Radio & TV Production",
                "Broadcast Production",
                "Digital Content Creation",
                "Content Strategy",
                "Social Media Management",
                "Influencer Marketing",

                // --- FALLBACK ---
                "Other"
        );
    }


    public static List<String> employmentType() {
        return List.of(
                "Private Sector",
                "Government / Public Sector",
                "Civil Services (IAS/IPS/IFS)",
                "Defense / Military",
                "Business / Entrepreneur",
                "Self Employed / Freelancer",
                "Professional (Doctor/Lawyer/CA)",
                "NGO / Social Sector",
                "Homemaker",
                "Student",
                "Not Working",
                "Retired"
        );
    }

    public static List<String> occupation() {
        return List.of(

                // ================= TECHNOLOGY & IT =================
                "Software Developer",
                "Frontend Developer",
                "Backend Developer",
                "Full Stack Developer",
                "Mobile App Developer",
                "Web Developer",
                "Game Developer",
                "Embedded Systems Engineer",
                "Firmware Engineer",
                "UI/UX Designer",
                "Product Designer",
                "Data Scientist",
                "Data Analyst",
                "Data Engineer",
                "AI Engineer",
                "Machine Learning Engineer",
                "Deep Learning Engineer",
                "DevOps Engineer",
                "Site Reliability Engineer",
                "Cloud Engineer",
                "Cloud Architect",
                "Cyber Security Analyst",
                "Security Engineer",
                "Ethical Hacker",
                "Network Engineer",
                "Network Administrator",
                "System Administrator",
                "Systems Architect",
                "Database Administrator",
                "IT Support Engineer",
                "IT Project Manager",
                "IT Program Manager",
                "IT Consultant",
                "ERP Consultant",
                "SAP Consultant",
                "Blockchain Developer",
                "Web3 Developer",
                "QA Engineer",
                "Test Automation Engineer",
                "Technical Writer",
                "Technology Evangelist",

                // ================= MEDICAL & HEALTHCARE =================
                "General Practitioner (MBBS)",
                "Family Physician",
                "Internal Medicine Doctor",
                "Surgeon",
                "General Surgeon",
                "Cardiologist",
                "Cardiac Surgeon",
                "Neurologist",
                "Neurosurgeon",
                "Dermatologist",
                "Cosmetologist",
                "Pediatrician",
                "Neonatologist",
                "Psychiatrist",
                "Clinical Psychologist",
                "Radiologist",
                "Oncologist",
                "Medical Oncologist",
                "Radiation Oncologist",
                "Endocrinologist",
                "Gastroenterologist",
                "Hepatologist",
                "Nephrologist",
                "Urologist",
                "Orthopedic Surgeon",
                "Sports Medicine Doctor",
                "Ophthalmologist",
                "Optometrist",
                "ENT Specialist",
                "Gynecologist",
                "Obstetrician",
                "Reproductive Medicine Specialist",
                "Anesthesiologist",
                "Pain Management Specialist",
                "Pathologist",
                "Hematologist",
                "Microbiologist",
                "Immunologist",
                "Dentist",
                "Oral Surgeon",
                "Orthodontist",
                "Prosthodontist",
                "Pharmacist",
                "Clinical Pharmacist",
                "Physiotherapist",
                "Occupational Therapist",
                "Speech Therapist",
                "Audiologist",
                "Nurse",
                "Nurse Practitioner",
                "Midwife",
                "Medical Lab Technician",
                "Radiology Technician",
                "Operation Theatre Technician",
                "Emergency Medical Technician (EMT)",
                "Paramedic",
                "Public Health Specialist",
                "Epidemiologist",
                "Nutritionist",
                "Dietitian",
                "Hospital Administrator",
                "Healthcare Manager",
                "Veterinary Doctor",
                "Homeopathic Doctor",
                "Ayurvedic Doctor",
                "Unani Doctor",

                // ================= ENGINEERING & ARCHITECTURE =================
                "Civil Engineer",
                "Structural Engineer",
                "Geotechnical Engineer",
                "Construction Engineer",
                "Mechanical Engineer",
                "Automobile Engineer",
                "Electrical Engineer",
                "Power Engineer",
                "Electronics Engineer",
                "Electronics Design Engineer",
                "Instrumentation Engineer",
                "Chemical Engineer",
                "Process Engineer",
                "Petroleum Engineer",
                "Mining Engineer",
                "Metallurgical Engineer",
                "Industrial Engineer",
                "Manufacturing Engineer",
                "Production Engineer",
                "Quality Engineer",
                "Safety Engineer",
                "Fire Safety Engineer",
                "Environmental Engineer",
                "Water Resources Engineer",
                "Transportation Engineer",
                "Marine Engineer",
                "Naval Architect",
                "Aerospace Engineer",
                "Aeronautical Engineer",
                "Biomedical Engineer",
                "Biotechnology Engineer",
                "Robotics Engineer",
                "Mechatronics Engineer",
                "Materials Engineer",
                "Nanotechnology Engineer",
                "Nuclear Engineer",
                "Renewable Energy Engineer",
                "Solar Energy Engineer",
                "Wind Energy Engineer",
                "Architect",
                "Landscape Architect",
                "Interior Designer",
                "Urban Planner",
                "Town Planner",
                "Quantity Surveyor",

                // ================= BUSINESS, FINANCE & MANAGEMENT =================
                "Business Owner",
                "Entrepreneur",
                "Startup Founder",
                "Managing Director",
                "Chief Executive Officer (CEO)",
                "Chief Operating Officer (COO)",
                "Chief Financial Officer (CFO)",
                "Chief Technology Officer (CTO)",
                "General Manager",
                "Operations Manager",
                "Project Manager",
                "Program Manager",
                "Product Manager",
                "Business Analyst",
                "Management Consultant",
                "Strategy Consultant",
                "Chartered Accountant (CA)",
                "Certified Public Accountant (CPA)",
                "Cost Accountant",
                "Auditor",
                "Tax Consultant",
                "Accountant",
                "Financial Analyst",
                "Investment Banker",
                "Equity Analyst",
                "Portfolio Manager",
                "Fund Manager",
                "Stock Broker",
                "Commodity Trader",
                "Forex Trader",
                "Actuary",
                "Risk Manager",
                "Compliance Officer",
                "Company Secretary (CS)",
                "Human Resources Manager",
                "Talent Acquisition Specialist",
                "Payroll Manager",
                "Marketing Manager",
                "Brand Manager",
                "Digital Marketing Manager",
                "Growth Marketer",
                "Sales Manager",
                "Business Development Manager",
                "Customer Success Manager",
                "Supply Chain Manager",
                "Logistics Manager",
                "Procurement Manager",
                "Operations Analyst",

                // ================= LAW & LEGAL =================
                "Advocate",
                "Lawyer",
                "Senior Advocate",
                "Judge",
                "Magistrate",
                "Judicial Officer",
                "Corporate Lawyer",
                "Criminal Lawyer",
                "Civil Lawyer",
                "Family Lawyer",
                "Islamic Law Expert",
                "International Lawyer",
                "Intellectual Property Lawyer",
                "Patent Attorney",
                "Trademark Attorney",
                "Tax Lawyer",
                "Cyber Lawyer",
                "Legal Consultant",
                "Legal Advisor",
                "In-House Counsel",
                "Notary Public",
                "Public Prosecutor",
                "Legal Secretary",
                "Paralegal",

                // ================= EDUCATION & RESEARCH =================
                "Professor",
                "Associate Professor",
                "Assistant Professor",
                "Lecturer",
                "School Teacher",
                "Primary School Teacher",
                "Secondary School Teacher",
                "Special Education Teacher",
                "Montessori Teacher",
                "Principal",
                "Headmaster",
                "Academic Coordinator",
                "Education Consultant",
                "Instructional Designer",
                "Curriculum Developer",
                "Research Scientist",
                "Research Associate",
                "Postdoctoral Researcher",
                "Scientist",
                "Lab Scientist",
                "Education Administrator",
                "University Administrator",
                "Librarian",
                "Archivist",
                "Tutor",
                "Private Instructor",

                // ================= GOVERNMENT, DEFENSE & PUBLIC SERVICE =================
                "Civil Services Officer (IAS/IPS/IFS)",
                "Government Officer",
                "Government Clerk",
                "Public Sector Employee",
                "Police Officer",
                "Police Inspector",
                "Army Officer",
                "Army Personnel",
                "Navy Officer",
                "Air Force Officer",
                "Defense Analyst",
                "Intelligence Officer",
                "Customs Officer",
                "Excise Officer",
                "Income Tax Officer",
                "Diplomat",
                "Foreign Service Officer",
                "Municipal Officer",
                "Postal Officer",
                "Railway Officer",
                "Railway Employee",
                "Firefighter",
                "Disaster Management Officer",

                // ================= ARTS, MEDIA & CREATIVE =================
                "Journalist",
                "News Reporter",
                "Editor",
                "Copy Editor",
                "Content Writer",
                "Technical Content Writer",
                "Script Writer",
                "Screenwriter",
                "Author",
                "Blogger",
                "Photographer",
                "Photojournalist",
                "Videographer",
                "Cinematographer",
                "Film Director",
                "Assistant Director",
                "Film Producer",
                "Music Director",
                "Music Composer",
                "Singer",
                "Musician",
                "Instrumentalist",
                "Sound Engineer",
                "Actor",
                "Actress",
                "Theatre Artist",
                "Model",
                "Fashion Model",
                "Fashion Designer",
                "Textile Designer",
                "Graphic Designer",
                "Motion Graphics Designer",
                "Animator",
                "VFX Artist",
                "Game Artist",
                "Illustrator",
                "Fine Artist",
                "Painter",
                "Sculptor",
                "Art Director",
                "Creative Director",
                "Social Media Manager",
                "Social Media Influencer",
                "YouTuber",
                "Podcaster",

                // ================= HOSPITALITY, TRAVEL & AVIATION =================
                "Pilot",
                "Commercial Pilot",
                "Helicopter Pilot",
                "Flight Instructor",
                "Flight Attendant",
                "Cabin Crew",
                "Air Traffic Controller",
                "Aircraft Maintenance Engineer",
                "Aviation Safety Officer",
                "Airport Operations Manager",
                "Hotel Manager",
                "Resort Manager",
                "Restaurant Manager",
                "Chef",
                "Executive Chef",
                "Sous Chef",
                "Pastry Chef",
                "Catering Manager",
                "Travel Agent",
                "Tour Operator",
                "Tour Guide",
                "Event Manager",
                "Event Planner",
                "Cruise Staff",

                // ================= BUSINESS, TRADE & SKILLED WORK =================
                "Retail Shop Owner",
                "Wholesale Trader",
                "Distributor",
                "Importer",
                "Exporter",
                "E-Commerce Seller",
                "Franchise Owner",
                "Real Estate Agent",
                "Real Estate Broker",
                "Property Consultant",
                "Property Manager",
                "Construction Contractor",
                "Interior Contractor",
                "Freelancer",
                "Independent Consultant",
                "Commission Agent",

                // ================= AGRICULTURE & RURAL =================
                "Farmer",
                "Agricultural Scientist",
                "Agronomist",
                "Horticulturist",
                "Plant Breeder",
                "Soil Scientist",
                "Dairy Farmer",
                "Poultry Farmer",
                "Fish Farmer",
                "Fisherman",
                "Aquaculture Specialist",
                "Veterinary Assistant",
                "Agricultural Officer",
                "Agri-Business Manager",
                "Rural Development Officer",

                // ================= SKILLED & VOCATIONAL =================
                "Electrician",
                "Industrial Electrician",
                "Plumber",
                "Pipe Fitter",
                "Carpenter",
                "Furniture Maker",
                "Welder",
                "Fabricator",
                "Machinist",
                "CNC Operator",
                "Tool and Die Maker",
                "Mechanic",
                "Automobile Mechanic",
                "Diesel Mechanic",
                "Heavy Equipment Operator",
                "Crane Operator",
                "Forklift Operator",
                "HVAC Technician",
                "Refrigeration Technician",
                "Lift Technician",
                "Solar Panel Technician",
                "Wind Turbine Technician",

                // ================= ISLAMIC & RELIGIOUS SERVICES =================
                "Imam",
                "Khateeb",
                "Muezzin",
                "Islamic Scholar",
                "Religious Teacher (Maulana/Alim)",
                "Hafiz-e-Quran",
                "Quran Teacher",
                "Tajweed Instructor",
                "Mufti",
                "Qazi",
                "Islamic Jurist",
                "Sharia Advisor",
                "Islamic Finance Consultant",
                "Waqf Administrator",

                // ================= TRANSPORT & LOGISTICS =================
                "Truck Driver",
                "Bus Driver",
                "Taxi Driver",
                "Chauffeur",
                "Delivery Driver",
                "Courier",
                "Logistics Coordinator",
                "Logistics Supervisor",
                "Warehouse Manager",
                "Warehouse Supervisor",
                "Inventory Controller",
                "Supply Chain Executive",
                "Shipping Coordinator",
                "Freight Forwarder",
                "Merchant Navy Officer",
                "Seafarer",

                // ================= PERSONAL SERVICES =================
                "Beautician",
                "Cosmetologist",
                "Hair Stylist",
                "Makeup Artist",
                "Spa Therapist",
                "Fitness Trainer",
                "Personal Trainer",
                "Yoga Instructor",
                "Sports Coach",
                "Athlete",
                "Professional Sports Person",

                // ================= OTHER =================
                "Homemaker",
                "Housewife",
                "Househusband",
                "Student",
                "Intern",
                "Apprentice",
                "Retired",
                "Unemployed",
                "Self-Employed",
                "NGO Worker",
                "Social Worker",
                "Community Organizer",
                "Volunteer",
                "Caregiver",
                "Personal Assistant",
                "Office Assistant"
        );
    }


    public static List<String> annualIncome() {
        return List.of(
                "No Income",
                "Under 2 Lakhs",
                "2 - 4 Lakhs",
                "4 - 7 Lakhs",
                "7 - 10 Lakhs",
                "10 - 15 Lakhs",
                "15 - 20 Lakhs",
                "20 - 30 Lakhs",
                "30 - 50 Lakhs",
                "50 - 75 Lakhs",
                "75 Lakhs - 1 Crore",
                "1 Crore - 2 Crores",
                "2 Crores+"
        );
    }

    // --------------------------------------------------
    // RELIGIOUS (MVP)
    // --------------------------------------------------

    public static List<String> sect() {
        return List.of(
                "Sunni",
                "Shia",
                "Ahle Hadees",
                "Other"
        );
    }

    public static List<String> caste() {
        return List.of(
                "Syed",
                "Sheikh",
                "Pathan",
                "Ansari",
                "Memon",
                "Other"
        );
    }

    // --------------------------------------------------
    // LOCATION (OPTIONAL FUTURE)
    // --------------------------------------------------

    public static List<String> countries() {
        return List.of(
                "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan",
                "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi",
                "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo (Congo-Brazzaville)", "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czechia (Czech Republic)",
                "Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica", "Dominican Republic",
                "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini", "Ethiopia",
                "Fiji", "Finland", "France",
                "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana",
                "Haiti", "Holy See", "Honduras", "Hungary",
                "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Ivory Coast",
                "Jamaica", "Japan", "Jordan",
                "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan",
                "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg",
                "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar (Burma)",
                "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "North Macedonia", "Norway",
                "Oman",
                "Pakistan", "Palau", "Palestine State", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal",
                "Qatar",
                "Romania", "Russia", "Rwanda",
                "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Sweden", "Switzerland", "Syria",
                "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu",
                "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States of America", "Uruguay", "Uzbekistan",
                "Vanuatu", "Venezuela", "Vietnam",
                "Yemen",
                "Zambia", "Zimbabwe"
        );
    }


    public static List<String> languages() {
        return List.of(

                // ---------- GLOBAL & MAJOR ----------
                "English", "English (UK)", "English (US)", "English (Australia)", "English (Canada)",
                "Mandarin Chinese", "Cantonese", "Wu Chinese", "Jin Chinese", "Hakka Chinese",
                "Spanish", "French", "Portuguese", "German", "Italian", "Russian",
                "Japanese", "Korean", "Turkish", "Vietnamese", "Thai",
                "Indonesian", "Malay", "Filipino (Tagalog)", "Burmese",

                // ---------- SOUTH ASIA ----------
                "Hindi", "Urdu", "Bengali", "Punjabi", "Gujarati", "Marathi", "Tamil",
                "Telugu", "Kannada", "Malayalam", "Odia", "Assamese", "Maithili",
                "Bhojpuri", "Awadhi", "Magahi", "Chhattisgarhi", "Rajasthani",
                "Marwari", "Mewati", "Kumaoni", "Garhwali", "Dogri", "Kashmiri",
                "Sindhi", "Saraiki", "Tulu", "Kodava", "Konkani", "Manipuri",
                "Bodo", "Santhali", "Mundari", "Ho", "Khasi", "Garo", "Mizo",
                "Nagamese", "Kokborok", "Lepcha",

                // ---------- MIDDLE EAST & ISLAMIC WORLD ----------
                "Arabic (Modern Standard)", "Arabic (Egyptian)", "Arabic (Levantine)",
                "Arabic (Gulf)", "Arabic (Maghrebi)", "Arabic (Iraqi)", "Arabic (Yemeni)",
                "Persian (Farsi)", "Dari", "Tajik", "Pashto", "Balochi",
                "Kurdish (Kurmanji)", "Kurdish (Sorani)", "Luri",
                "Turkmen", "Kazakh", "Uzbek", "Kyrgyz", "Uyghur",
                "Tatar", "Bashkir", "Chechen", "Circassian",

                // ---------- EUROPE ----------
                "Dutch", "Afrikaans", "Swedish", "Norwegian", "Danish", "Finnish",
                "Icelandic", "Irish", "Scottish Gaelic", "Welsh", "Breton",
                "Basque", "Catalan", "Galician", "Occitan",
                "Polish", "Czech", "Slovak", "Hungarian", "Romanian",
                "Bulgarian", "Serbian", "Croatian", "Bosnian", "Slovenian",
                "Macedonian", "Albanian", "Greek", "Ukrainian", "Belarusian",
                "Lithuanian", "Latvian", "Estonian", "Maltese", "Luxembourgish",
                "Yiddish", "Romani",

                // ---------- AFRICA ----------
                "Swahili", "Hausa", "Yoruba", "Igbo", "Amharic", "Oromo",
                "Tigrinya", "Somali", "Afar", "Berber (Tamazight)", "Kabyle",
                "Wolof", "Fula", "Bambara", "Mandinka", "Ewe",
                "Twi (Akan)", "Ga", "Lingala", "Kikuyu", "Kinyarwanda",
                "Kirundi", "Luganda", "Shona", "Xitsonga", "Sesotho",
                "Tswana", "Venda", "Zulu", "Xhosa", "Ndebele",

                // ---------- EAST & SOUTHEAST ASIA ----------
                "Khmer", "Lao", "Hmong", "Karen", "Shan", "Mon",
                "Tibetan", "Dzongkha", "Nepali", "Newari",
                "Sinhala", "Dhivehi", "Acehnese", "Batak",
                "Javanese", "Sundanese", "Balinese",

                // ---------- AMERICAS ----------
                "Quechua", "Aymara", "Guarani", "Nahuatl", "Maya",
                "Zapotec", "Mixtec", "Navajo", "Cherokee",
                "Inuktitut", "Cree", "Ojibwe",
                "Haitian Creole", "Papiamento",

                // ---------- OCEANIA ----------
                "Maori", "Samoan", "Tongan", "Fijian",
                "Hawaiian", "Tahiti", "Tok Pisin",

                // ---------- SIGN LANGUAGES ----------
                "American Sign Language (ASL)", "British Sign Language (BSL)",
                "Indian Sign Language", "Arabic Sign Language",

                // ---------- CLASSICAL & LITURGICAL ----------
                "Classical Arabic", "Quranic Arabic",
                "Sanskrit", "Pali", "Prakrit",
                "Latin", "Classical Greek",
                "Hebrew (Biblical)", "Aramaic", "Syriac",
                "Coptic", "Avestan"
        );
    }


    public static List<String> sects() {
        return List.of(
                "Sunni",
                "Shia",
                "Hanafi",
                "Shafi",
                "Maliki",
                "Hanbali",
                "Salafi",
                "Ahle Hadith",
                "Deobandi",
                "Barelvi",
                "Sufi",
                "Other",
                "Prefer Not to Say"
        );
    }


    public static List<String> castes() {
        return List.of(
                "Abbasi",
                "Afridi",
                "Ansari",
                "Arain",
                "Attar",
                "Awan",
                "Baig",
                "Balti",
                "Barakzai",
                "Beary",
                "Bhangi Muslim",
                "Bohra",
                "Bukhari",
                "Chauhan",
                "Chhetri Muslims",
                "Chishti",
                "Chughtai",
                "Darvesh",
                "Dhivehi Muslim",
                "Dhunia",
                "Dudekula",
                "Durrani",
                "Faqir",
                "Farooqi",
                "Gilani",
                "Ghilzai",
                "Gujjar",
                "Hashmi",
                "Howladar",
                "Jat Muslim",
                "Kamein",
                "Kazmi",
                "Khokhar",
                "Khoja",
                "Kunjra",
                "Labbai",
                "Lalbegi",
                "Malays",
                "Malik",
                "Mapila",
                "Mappila",
                "Mansoori",
                "Memon",
                "Mehtar Muslim",
                "Mirza",
                "Mochi Muslim",
                "Momin Ansari",
                "Mohmand",
                "Moors",
                "Miya Muslims",
                "Naqvi",
                "Nalband",
                "Navayath",
                "Panthay",
                "Pathan",
                "Pathari",
                "Qasai",
                "Qureshi",
                "Rajput Muslim",
                "Rangrez",
                "Rowther",
                "Sadat",
                "Saifi",
                "Salmani",
                "Sayed",
                "Sheikh",
                "Shamshad",
                "Siddiqui",
                "Syed",
                "Taluqdar",
                "Teli",
                "Usmani",
                "Yusufzai",
                "Zerbadi"
        );
    }


    public static List<String> namazFrequencies() {
        return List.of(
                "Prays Five Times Daily",
                "Prays Regularly",
                "Prays Occasionally",
                "Prays on Fridays Only",
                "Prays During Ramadan Only",
                "Does Not Pray Regularly",
                "Learning to Pray",
                "Prefer Not to Say"
        );
    }

    public static List<String> readQuranOptions() {
        return List.of(
                "Reads Quran Daily",
                "Reads Quran Regularly",
                "Reads Quran Occasionally",
                "Reads Quran Rarely",
                "Can Read Quran with Tajweed",
                "Can Read Quran (Basic)",
                "Cannot Read Quran",
                "Learning to Read Quran",
                "Prefer Not to Say"
        );
    }

    public static List<String> halalDietOptions() {
        return List.of(
                "Strictly Halal",
                "Halal with No Alcohol",
                "Mostly Halal",
                "Occasionally Non-Halal",
                "Vegetarian (Halal)",
                "Eggetarian (Halal)",
                "Vegan (Halal)",
                "Prefer Home-Cooked Halal Food",
                "Halal but Eats Outside Occasionally",
                "Not Very Particular"
        );
    }


    public static List<String> beardHijabOptions() {
        return List.of(
                "Keeps Beard",
                "Keeps Beard Occasionally",
                "Clean Shaven",
                "Planning to Grow Beard",
                "Wears Hijab",
                "Wears Hijab Occasionally",
                "Does Not Wear Hijab",
                "Planning to Wear Hijab",
                "Prefers Modest Dressing",
                "Not Particular"
        );
    }


    public static List<String> hajjUmrahOptions() {
        return List.of(
                "Performed Hajj",
                "Performed Umrah",
                "Performed Both Hajj & Umrah",
                "Planning to Perform",
                "Not Performed Yet",
                "Not Applicable",
                "Prefer Not to Say"
        );
    }


    public static List<String> religiousObservationLevels() {
        return List.of(
                "Very Religious",
                "Religious",
                "Moderately Religious",
                "Practicing Muslim",
                "Occasionally Practicing",
                "Spiritual but Not Strict",
                "Traditional",
                "Liberal",
                "Not Very Religious",
                "Prefer Not to Say"
        );
    }


    public static List<String> familyStatus() {
        return List.of(
                "Lower Middle Class",
                "Middle Class",
                "Upper Middle Class",
                "Affluent"
        );
    }

    public static List<String> familyType() {
        return List.of(
                "Nuclear Family",
                "Joint Family"
        );
    }

    public static List<String> fatherOccupation() {
        return List.of(
                "Business",
                "Private Job",
                "Government Job",
                "Retired",
                "Farmer",
                "Self Employed",
                "Not Employed",
                "Passed Away"
        );
    }


    public static List<String> siblingCount() {
        return List.of(
                "No Siblings",
                "1",
                "2",
                "3",
                "4",
                "5+"
        );
    }




}
