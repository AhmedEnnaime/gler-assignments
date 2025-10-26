import React, { useState } from 'react';
import {
  Box,
  Typography,
  Tabs,
  Tab,
  TextField,
  InputAdornment,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Checkbox,
  IconButton,
  Chip,
  Pagination,
  TableSortLabel,
} from '@mui/material';
import { Search as SearchIcon, Edit as EditIcon } from '@mui/icons-material';
import type { User } from '../interfaces/User';

interface DataTableProps {
  users: User[];
  searchTerm: string;
  onSearchChange: (term: string) => void;
  activeTab: number;
  onTabChange: (tab: number) => void;
  onEditUser: (user: User) => void;
}

type SortField = 'email' | 'phoneNumber' | 'postcode' | 'vendorType' | 'serviceOffering' | 'signupDate' | 'status';
type SortDirection = 'asc' | 'desc';

const DataTable: React.FC<DataTableProps> = ({
  users,
  searchTerm,
  onSearchChange,
  activeTab,
  onTabChange,
  onEditUser,
}) => {
  const [sortField, setSortField] = useState<SortField>('email');
  const [sortDirection, setSortDirection] = useState<SortDirection>('asc');
  const [selectedUsers, setSelectedUsers] = useState<Set<string>>(new Set());
  const [currentPage, setCurrentPage] = useState(1);
  const rowsPerPage = 10;

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'Onboarded':
        return 'success';
      case 'Rejected':
        return 'error';
      default:
        return 'default';
    }
  };

  const handleSort = (field: SortField) => {
    if (sortField === field) {
      setSortDirection(sortDirection === 'asc' ? 'desc' : 'asc');
    } else {
      setSortField(field);
      setSortDirection('asc');
    }
  };

  const handleSelectAll = () => {
    const allUserIds = paginatedUsers.map(user => user.id);
    setSelectedUsers(new Set(allUserIds));
  };

  const handleDeselectAll = () => {
    setSelectedUsers(new Set());
  };

  const handleSelectUser = (userId: string, checked: boolean) => {
    const newSelected = new Set(selectedUsers);
    if (checked) {
      newSelected.add(userId);
    } else {
      newSelected.delete(userId);
    }
    setSelectedUsers(newSelected);
  };

  const sortedUsers = [...users].sort((a, b) => {
    let aValue: string | number = a[sortField];
    let bValue: string | number = b[sortField];
    if (sortField === 'signupDate') {
      aValue = new Date(aValue).getTime();
      bValue = new Date(bValue).getTime();
    }
    if (typeof aValue === 'string' && typeof bValue === 'string') {
      aValue = aValue.toLowerCase();
      bValue = bValue.toLowerCase();
    }

    if (aValue < bValue) return sortDirection === 'asc' ? -1 : 1;
    if (aValue > bValue) return sortDirection === 'asc' ? 1 : -1;
    return 0;
  });

  const totalPages = Math.ceil(sortedUsers.length / rowsPerPage);
  const startIndex = (currentPage - 1) * rowsPerPage;
  const paginatedUsers = sortedUsers.slice(startIndex, startIndex + rowsPerPage);
  const isAllSelected = paginatedUsers.length > 0 && paginatedUsers.every(user => selectedUsers.has(user.id));

  return (
    <Box sx={{ flex: 1, p: { xs: 2, md: 3 } }}>
      <Box sx={{ display: { xs: 'none', md: 'flex' }, justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
        <Typography variant="h4" sx={{ fontWeight: 'bold', fontSize: { xs: '1.5rem', md: '2rem' } }}>
          Waitlist
        </Typography>
      </Box>
      <Box sx={{ borderBottom: 1, borderColor: 'divider', mb: 3 }}>
        <Tabs value={activeTab} onChange={(_, newValue) => onTabChange(newValue)}>
          <Tab
            label="Service Providers"
            sx={{
              fontWeight: activeTab === 0 ? 'bold' : 'normal',
              color: activeTab === 0 ? 'primary.main' : 'text.primary',
            }}
          />
          <Tab
            label="Customers"
            sx={{
              fontWeight: activeTab === 1 ? 'bold' : 'normal',
              color: activeTab === 1 ? 'primary.main' : 'text.primary',
            }}
          />
        </Tabs>
      </Box>
      <Box sx={{ mb: 3, display: 'flex', justifyContent: 'flex-end' }}>
        <TextField
          size="small"
          placeholder="Search User"
          value={searchTerm}
          onChange={(e) => onSearchChange(e.target.value.trim())}
          slotProps={{
            input: {
              startAdornment: (
                <InputAdornment position="start">
                  <SearchIcon />
                </InputAdornment>
              ),
            },
          }}
          sx={{ width: { xs: '100%', sm: 300 } }}
        />
      </Box>
      <TableContainer 
        component={Paper} 
        sx={{ 
          boxShadow: 'none', 
          overflowX: 'auto', 
          backgroundColor: 'white', 
          borderRadius: 2,
          '&::-webkit-scrollbar': {
            height: '8px',
          },
          '&::-webkit-scrollbar-track': {
            backgroundColor: '#f1f1f1',
            borderRadius: '4px',
          },
          '&::-webkit-scrollbar-thumb': {
            backgroundColor: '#c1c1c1',
            borderRadius: '4px',
            '&:hover': {
              backgroundColor: '#a8a8a8',
            },
          },
        }}
      >
        <Table sx={{ minWidth: 800 }}>
          <TableHead>
            <TableRow sx={{ backgroundColor: '#e9ecef' }}>
              <TableCell padding="checkbox">
                <Checkbox
                  checked={isAllSelected}
                  indeterminate={selectedUsers.size > 0 && !isAllSelected}
                  onChange={() => isAllSelected ? handleDeselectAll() : handleSelectAll()}
                />
              </TableCell>
              <TableCell sx={{ fontWeight: 'bold' }}>
                <TableSortLabel
                  active={sortField === 'email'}
                  direction={sortField === 'email' ? sortDirection : 'asc'}
                  onClick={() => handleSort('email')}
                >
                  Email
                </TableSortLabel>
              </TableCell>
              <TableCell sx={{ fontWeight: 'bold' }}>
                <TableSortLabel
                  active={sortField === 'phoneNumber'}
                  direction={sortField === 'phoneNumber' ? sortDirection : 'asc'}
                  onClick={() => handleSort('phoneNumber')}
                >
                  Phone Number
                </TableSortLabel>
              </TableCell>
              <TableCell sx={{ fontWeight: 'bold' }}>
                <TableSortLabel
                  active={sortField === 'postcode'}
                  direction={sortField === 'postcode' ? sortDirection : 'asc'}
                  onClick={() => handleSort('postcode')}
                >
                  Postcode
                </TableSortLabel>
              </TableCell>
              <TableCell sx={{ fontWeight: 'bold' }}>
                <TableSortLabel
                  active={sortField === 'vendorType'}
                  direction={sortField === 'vendorType' ? sortDirection : 'asc'}
                  onClick={() => handleSort('vendorType')}
                >
                  Vendor Type
                </TableSortLabel>
              </TableCell>
              <TableCell sx={{ fontWeight: 'bold' }}>
                <TableSortLabel
                  active={sortField === 'serviceOffering'}
                  direction={sortField === 'serviceOffering' ? sortDirection : 'asc'}
                  onClick={() => handleSort('serviceOffering')}
                >
                  Service Offering
                </TableSortLabel>
              </TableCell>
              <TableCell sx={{ fontWeight: 'bold' }}>
                <TableSortLabel
                  active={sortField === 'signupDate'}
                  direction={sortField === 'signupDate' ? sortDirection : 'asc'}
                  onClick={() => handleSort('signupDate')}
                >
                  Signup Date
                </TableSortLabel>
              </TableCell>
              <TableCell sx={{ fontWeight: 'bold' }}>
                <TableSortLabel
                  active={sortField === 'status'}
                  direction={sortField === 'status' ? sortDirection : 'asc'}
                  onClick={() => handleSort('status')}
                >
                  Status
                </TableSortLabel>
              </TableCell>
              <TableCell sx={{ fontWeight: 'bold' }}>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {paginatedUsers.map((user, index) => (
              <TableRow 
                key={user.id} 
                hover
                sx={{
                  backgroundColor: index % 2 === 0 ? 'white' : '#f8f9fa',
                  '&:hover': {
                    backgroundColor: index % 2 === 0 ? '#f0f0f0' : '#e9ecef',
                  },
                }}
              >
                <TableCell padding="checkbox">
                  <Checkbox
                    checked={selectedUsers.has(user.id)}
                    onChange={(e) => handleSelectUser(user.id, e.target.checked)}
                  />
                </TableCell>
                <TableCell>{user.email}</TableCell>
                <TableCell>{user.phoneNumber}</TableCell>
                <TableCell>{user.postcode}</TableCell>
                <TableCell>{user.vendorType}</TableCell>
                <TableCell>{user.serviceOffering}</TableCell>
                <TableCell>{user.signupDate}</TableCell>
                <TableCell>
                  {user.status === '-' ? (
                    '-'
                  ) : (
                    <Chip
                      label={user.status}
                      color={getStatusColor(user.status)}
                      size="small"
                    />
                  )}
                </TableCell>
                <TableCell>
                  <IconButton size="small" onClick={() => onEditUser(user)}>
                    <EditIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <Box sx={{ display: 'flex', justifyContent: 'flex-end', mt: 3 }}>
        <Pagination
          count={totalPages}
          page={currentPage}
          onChange={(_, page) => setCurrentPage(page)}
          color="primary"
          shape="rounded"
          showFirstButton
          showLastButton
        />
      </Box>
    </Box>
  );
};

export default DataTable;
