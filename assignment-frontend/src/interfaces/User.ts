export interface User {
  id: string;
  email: string;
  phoneNumber: string;
  postcode: string;
  vendorType: 'Independent' | 'Company';
  serviceOffering: 'Housekeeping' | 'Window Cleaning' | 'Car Valet';
  signupDate: string;
  status: 'Onboarded' | 'Rejected' | '-';
}

export interface FilterOptions {
  postcode?: string;
  registrationStatus: {
    onboarded: boolean;
    rejected: boolean;
  };
  dateRegistered: {
    start: string;
    end: string;
  };
  vendorType: {
    independent: boolean;
    company: boolean;
  };
  serviceOffering: {
    housekeeping: boolean;
    windowCleaning: boolean;
    carValet: boolean;
  };
}

export interface NavigationItem {
  label: string;
  path: string;
  active?: boolean;
}
