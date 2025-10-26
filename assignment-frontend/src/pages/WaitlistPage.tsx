import React, { useState, useMemo } from 'react';
import { Box, useMediaQuery, useTheme, Drawer, IconButton, Typography } from '@mui/material';
import { Menu as MenuIcon } from '@mui/icons-material';
import Header from '../components/Header';
import Sidebar from '../components/Sidebar';
import DataTable from '../components/DataTable';
import UserDetailsModal from '../components/UserDetailsModal';
import type { User, FilterOptions } from '../interfaces/User';

const mockUsers: User[] = [
  {
    id: '1',
    email: 'jonesadam@gmail.com',
    phoneNumber: '+44 20 7946 0958',
    postcode: 'SW1A 1AA',
    vendorType: 'Independent',
    serviceOffering: 'Housekeeping',
    signupDate: '01/05/2024',
    status: '-',
  },
  {
    id: '2',
    email: 'Gler@app.com',
    phoneNumber: '+44 20 7946 0958',
    postcode: 'M1 1AE',
    vendorType: 'Company',
    serviceOffering: 'Window Cleaning',
    signupDate: '21/03/2025',
    status: 'Onboarded',
  },
  {
    id: '3',
    email: 'Albertwatson@gmail.com',
    phoneNumber: '+44 20 7946 0958',
    postcode: 'OX1 2JD',
    vendorType: 'Independent',
    serviceOffering: 'Housekeeping',
    signupDate: '11/10/2023',
    status: 'Rejected',
  },
  {
    id: '4',
    email: 'sarah.johnson@email.com',
    phoneNumber: '+44 20 7946 0959',
    postcode: 'E1 6AN',
    vendorType: 'Company',
    serviceOffering: 'Car Valet',
    signupDate: '15/02/2024',
    status: 'Onboarded',
  },
  {
    id: '5',
    email: 'mike.wilson@clean.com',
    phoneNumber: '+44 20 7946 0960',
    postcode: 'N1 7GU',
    vendorType: 'Independent',
    serviceOffering: 'Window Cleaning',
    signupDate: '03/08/2024',
    status: '-',
  },
  {
    id: '6',
    email: 'lisa.anderson@email.com',
    phoneNumber: '+44 20 7946 0961',
    postcode: 'W1K 6TF',
    vendorType: 'Company',
    serviceOffering: 'Housekeeping',
    signupDate: '30/01/2024',
    status: 'Onboarded',
  },
  {
    id: '7',
    email: 'david.brown@services.com',
    phoneNumber: '+44 20 7946 0962',
    postcode: 'SE1 9RT',
    vendorType: 'Independent',
    serviceOffering: 'Car Valet',
    signupDate: '22/06/2024',
    status: 'Rejected',
  },
  {
    id: '8',
    email: 'emma.davis@clean.co.uk',
    phoneNumber: '+44 20 7946 0963',
    postcode: 'NW1 6XE',
    vendorType: 'Company',
    serviceOffering: 'Window Cleaning',
    signupDate: '14/09/2024',
    status: 'Onboarded',
  },
  {
    id: '9',
    email: 'james.miller@services.co.uk',
    phoneNumber: '+44 20 7946 0964',
    postcode: 'SW6 1AA',
    vendorType: 'Independent',
    serviceOffering: 'Housekeeping',
    signupDate: '07/12/2023',
    status: '-',
  },
  {
    id: '10',
    email: 'sophie.taylor@email.com',
    phoneNumber: '+44 20 7946 0965',
    postcode: 'EC1A 1BB',
    vendorType: 'Company',
    serviceOffering: 'Car Valet',
    signupDate: '19/04/2024',
    status: 'Onboarded',
  },
  {
    id: '11',
    email: 'robert.garcia@clean.com',
    phoneNumber: '+44 20 7946 0966',
    postcode: 'WC1A 2SE',
    vendorType: 'Independent',
    serviceOffering: 'Window Cleaning',
    signupDate: '25/07/2024',
    status: 'Rejected',
  },
  {
    id: '12',
    email: 'jennifer.martinez@services.com',
    phoneNumber: '+44 20 7946 0967',
    postcode: 'SW3 4RD',
    vendorType: 'Company',
    serviceOffering: 'Housekeeping',
    signupDate: '11/11/2023',
    status: 'Onboarded',
  },
  {
    id: '13',
    email: 'william.rodriguez@email.com',
    phoneNumber: '+44 20 7946 0968',
    postcode: 'N7 8QJ',
    vendorType: 'Independent',
    serviceOffering: 'Car Valet',
    signupDate: '05/03/2024',
    status: '-',
  },
  {
    id: '14',
    email: 'maria.lopez@clean.co.uk',
    phoneNumber: '+44 20 7946 0969',
    postcode: 'E2 8HD',
    vendorType: 'Company',
    serviceOffering: 'Window Cleaning',
    signupDate: '28/10/2024',
    status: 'Onboarded',
  },
  {
    id: '15',
    email: 'thomas.gonzalez@services.co.uk',
    phoneNumber: '+44 20 7946 0970',
    postcode: 'W2 3QZ',
    vendorType: 'Independent',
    serviceOffering: 'Housekeeping',
    signupDate: '16/05/2024',
    status: 'Rejected',
  },
  {
    id: '16',
    email: 'patricia.hernandez@email.com',
    phoneNumber: '+44 20 7946 0971',
    postcode: 'SE5 9ST',
    vendorType: 'Company',
    serviceOffering: 'Car Valet',
    signupDate: '09/01/2024',
    status: 'Onboarded',
  },
  {
    id: '17',
    email: 'christopher.smith@clean.com',
    phoneNumber: '+44 20 7946 0972',
    postcode: 'NW3 2TH',
    vendorType: 'Independent',
    serviceOffering: 'Window Cleaning',
    signupDate: '13/08/2024',
    status: '-',
  },
  {
    id: '18',
    email: 'linda.johnson@services.com',
    phoneNumber: '+44 20 7946 0973',
    postcode: 'SW7 2AZ',
    vendorType: 'Company',
    serviceOffering: 'Housekeeping',
    signupDate: '04/12/2023',
    status: 'Onboarded',
  },
  {
    id: '19',
    email: 'daniel.williams@email.com',
    phoneNumber: '+44 20 7946 0974',
    postcode: 'E14 5AB',
    vendorType: 'Independent',
    serviceOffering: 'Car Valet',
    signupDate: '27/06/2024',
    status: 'Rejected',
  },
  {
    id: '20',
    email: 'barbara.brown@clean.co.uk',
    phoneNumber: '+44 20 7946 0975',
    postcode: 'N4 3JX',
    vendorType: 'Company',
    serviceOffering: 'Window Cleaning',
    signupDate: '20/09/2024',
    status: 'Onboarded',
  },
];

const WaitlistPage: React.FC = () => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('md'));
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  const [activeTab, setActiveTab] = useState(0);
  const [filters, setFilters] = useState<FilterOptions>({
    registrationStatus: {
      onboarded: false,
      rejected: false,
    },
    dateRegistered: {
      start: '',
      end: '',
    },
    vendorType: {
      independent: false,
      company: false,
    },
    serviceOffering: {
      housekeeping: false,
      windowCleaning: false,
      carValet: false,
    },
  });
  const [users, setUsers] = useState<User[]>(mockUsers);
  const [selectedUser, setSelectedUser] = useState<User | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const matchesSearch = (user: User): boolean => {
    return !searchTerm || user.email.toLowerCase().includes(searchTerm.toLowerCase());
  };

  const matchesRegistrationStatus = (user: User): boolean => {
    const { onboarded, rejected } = filters.registrationStatus;
    if (onboarded && user.status !== 'Onboarded') return false;
    if (rejected && user.status !== 'Rejected') return false;
    return true;
  };

  const matchesPostcode = (user: User): boolean => {
    return !filters.postcode || user.postcode.toLowerCase().includes(filters.postcode.toLowerCase());
  };

  const matchesVendorType = (user: User): boolean => {
    const { independent, company } = filters.vendorType;
    if (independent && user.vendorType !== 'Independent') return false;
    if (company && user.vendorType !== 'Company') return false;
    return true;
  };

  const matchesServiceOffering = (user: User): boolean => {
    const { housekeeping, windowCleaning, carValet } = filters.serviceOffering;
    if (housekeeping && user.serviceOffering !== 'Housekeeping') return false;
    if (windowCleaning && user.serviceOffering !== 'Window Cleaning') return false;
    if (carValet && user.serviceOffering !== 'Car Valet') return false;
    return true;
  };

  const applyFilters = (user: User): boolean => {
    return (
      matchesSearch(user) &&
      matchesRegistrationStatus(user) &&
      matchesPostcode(user) &&
      matchesVendorType(user) &&
      matchesServiceOffering(user)
    );
  };

  const filteredUsers = useMemo(() => {
    return users.filter(applyFilters);
  }, [users, searchTerm, filters]);

  const handleFilterChange = (newFilters: FilterOptions) => {
    setFilters(newFilters);
  };

  const handleApplyFilters = () => {
  };

  const handleClearFilters = () => {
    setFilters({
      registrationStatus: {
        onboarded: false,
        rejected: false,
      },
      dateRegistered: {
        start: '',
        end: '',
      },
      vendorType: {
        independent: false,
        company: false,
      },
      serviceOffering: {
        housekeeping: false,
        windowCleaning: false,
        carValet: false,
      },
    });
  };

  const handleEditUser = (user: User) => {
    setSelectedUser(user);
    setIsModalOpen(true);
  };

  const handleUpdateUser = (updatedUser: User) => {
    setUsers(prevUsers => 
      prevUsers.map(user => 
        user.id === updatedUser.id ? updatedUser : user
      )
    );
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setSelectedUser(null);
  };

  const sidebarContent = (
    <Sidebar 
      onFilterChange={handleFilterChange}
      onApplyFilters={handleApplyFilters}
      onClearFilters={handleClearFilters}
    />
  );

  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
      <Header />
      <Box sx={{ display: 'flex', flex: 1, alignItems: 'flex-start' }}>
        {isMobile ? (
          <>
            <Drawer
              anchor="left"
              open={sidebarOpen}
              onClose={() => setSidebarOpen(false)}
              sx={{
                '& .MuiDrawer-paper': {
                  width: 300,
                  boxSizing: 'border-box',
                  backgroundColor: '#f8f9fa',
                  borderRadius: '0 16px 16px 0',
                },
              }}
            >
              {sidebarContent}
            </Drawer>
            <Box sx={{ flex: 1, position: 'relative' }}>
              <Box sx={{ 
                position: 'absolute', 
                top: 16, 
                left: 16, 
                right: 16,
                zIndex: 1,
                display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'center',
                pointerEvents: 'none'
              }}>
                <IconButton
                  onClick={() => setSidebarOpen(true)}
                  sx={{ 
                    backgroundColor: 'white',
                    boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
                    pointerEvents: 'auto',
                    '&:hover': {
                      backgroundColor: '#f5f5f5',
                    }
                  }}
                >
                  <MenuIcon />
                </IconButton>
                <Typography 
                  variant="h4" 
                  sx={{ 
                    fontWeight: 'bold', 
                    fontSize: { xs: '1.5rem', md: '2rem' },
                    color: 'text.primary',
                    pointerEvents: 'none'
                  }}
                >
                  Waitlist
                </Typography>
              </Box>
              <DataTable
                users={filteredUsers}
                searchTerm={searchTerm}
                onSearchChange={setSearchTerm}
                activeTab={activeTab}
                onTabChange={setActiveTab}
                onEditUser={handleEditUser}
              />
            </Box>
          </>
        ) : (
          <>
            {sidebarContent}
            <DataTable
              users={filteredUsers}
              searchTerm={searchTerm}
              onSearchChange={setSearchTerm}
              activeTab={activeTab}
              onTabChange={setActiveTab}
              onEditUser={handleEditUser}
            />
          </>
        )}
      </Box>
      <UserDetailsModal
        open={isModalOpen}
        onClose={handleCloseModal}
        user={selectedUser}
        onUpdateUser={handleUpdateUser}
      />
    </Box>
  );
};

export default WaitlistPage;
