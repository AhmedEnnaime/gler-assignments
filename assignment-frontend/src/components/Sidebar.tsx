import React, { useState } from 'react';
import {
  Box,
  Typography,
  TextField,
  FormGroup,
  FormControlLabel,
  Checkbox,
  Button,
  Paper,
} from '@mui/material';
import { CalendarToday as CalendarIcon } from '@mui/icons-material';
import type { FilterOptions } from '../interfaces/User';

interface SidebarProps {
  onFilterChange: (filters: FilterOptions) => void;
  onApplyFilters: () => void;
  onClearFilters: () => void;
}

const Sidebar: React.FC<SidebarProps> = ({ onFilterChange, onApplyFilters, onClearFilters }) => {
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

  const handleFilterChange = (newFilters: Partial<FilterOptions>) => {
    const updatedFilters = { ...filters, ...newFilters };
    setFilters(updatedFilters);
    onFilterChange(updatedFilters);
  };

  const handleCheckboxChange = (
    category: keyof FilterOptions,
    field: string,
    checked: boolean
  ) => {
    const updatedFilters = {
      ...filters,
      [category]: {
        ...(filters[category] as Record<string, boolean>),
        [field]: checked,
      },
    };
    setFilters(updatedFilters);
    onFilterChange(updatedFilters);
  };

  return (
    <Box sx={{ 
      width: 300, 
      p: 3, 
      backgroundColor: '#f8f9fa', 
      borderRight: '1px solid #e9ecef',
      borderRadius: 2,
      m: 2,
      height: 'fit-content'
    }}>
      <Typography
        variant="h6"
        sx={{
          fontWeight: 'bold',
          mb: 3,
          color: 'primary.main',
        }}
      >
        gler Admin Panel
      </Typography>
      <Paper
        sx={{
          p: 2,
          mb: 3,
          backgroundColor: '#e9ecef',
          borderRadius: 1,
        }}
      >
        <Typography variant="subtitle1" sx={{ fontWeight: 'bold' }}>
          User Management
        </Typography>
      </Paper>
      <Typography variant="h6" sx={{ mb: 2, fontWeight: 'bold' }}>
        Filters
      </Typography>
      <Box sx={{ mb: 3 }}>
        <Typography variant="subtitle2" sx={{ mb: 1 }}>
          Postcode
        </Typography>
        <TextField
          size="small"
          placeholder="ZIP"
          value={filters.postcode || ''}
          onChange={(e) =>
            handleFilterChange({ postcode: e.target.value })
          }
          sx={{ width: '100%' }}
        />
      </Box>
      <Box sx={{ mb: 3 }}>
        <Typography variant="subtitle2" sx={{ mb: 1 }}>
          Registration Status
        </Typography>
        <FormGroup>
          <FormControlLabel
            control={
              <Checkbox
                checked={filters.registrationStatus.onboarded}
                onChange={(e) =>
                  handleCheckboxChange(
                    'registrationStatus',
                    'onboarded',
                    e.target.checked
                  )
                }
              />
            }
            label="Onboarded"
          />
          <FormControlLabel
            control={
              <Checkbox
                checked={filters.registrationStatus.rejected}
                onChange={(e) =>
                  handleCheckboxChange(
                    'registrationStatus',
                    'rejected',
                    e.target.checked
                  )
                }
              />
            }
            label="Rejected"
          />
        </FormGroup>
      </Box>
      <Box sx={{ mb: 3 }}>
        <Typography variant="subtitle2" sx={{ mb: 1 }}>
          Date Registered
        </Typography>
        <Box sx={{ display: 'flex', gap: 1, mb: 1 }}>
          <TextField
            size="small"
            placeholder="MM/DD/YYYY"
            value={filters.dateRegistered.start}
            onChange={(e) =>
              handleFilterChange({
                dateRegistered: {
                  ...filters.dateRegistered,
                  start: e.target.value,
                },
              })
            }
            slotProps={{
              input: {
                endAdornment: <CalendarIcon sx={{ fontSize: 16 }} />,
              },
            }}
            sx={{ flex: 1 }}
          />
          <TextField
            size="small"
            placeholder="MM/DD/YYYY"
            value={filters.dateRegistered.end}
            onChange={(e) =>
              handleFilterChange({
                dateRegistered: {
                  ...filters.dateRegistered,
                  end: e.target.value,
                },
              })
            }
            slotProps={{
              input: {
                endAdornment: <CalendarIcon sx={{ fontSize: 16 }} />,
              },
            }}
            sx={{ flex: 1 }}
          />
        </Box>
        <Typography variant="caption" sx={{ color: 'text.secondary' }}>
          Start - End
        </Typography>
      </Box>
      <Box sx={{ mb: 3 }}>
        <Typography variant="subtitle2" sx={{ mb: 1 }}>
          Vendor Type
        </Typography>
        <FormGroup>
          <FormControlLabel
            control={
              <Checkbox
                checked={filters.vendorType.independent}
                onChange={(e) =>
                  handleCheckboxChange(
                    'vendorType',
                    'independent',
                    e.target.checked
                  )
                }
              />
            }
            label="Independent"
          />
          <FormControlLabel
            control={
              <Checkbox
                checked={filters.vendorType.company}
                onChange={(e) =>
                  handleCheckboxChange(
                    'vendorType',
                    'company',
                    e.target.checked
                  )
                }
              />
            }
            label="Company"
          />
        </FormGroup>
      </Box>
      <Box sx={{ mb: 3 }}>
        <Typography variant="subtitle2" sx={{ mb: 1 }}>
          Service Offering
        </Typography>
        <FormGroup>
          <FormControlLabel
            control={
              <Checkbox
                checked={filters.serviceOffering.housekeeping}
                onChange={(e) =>
                  handleCheckboxChange(
                    'serviceOffering',
                    'housekeeping',
                    e.target.checked
                  )
                }
              />
            }
            label="Housekeeping"
          />
          <FormControlLabel
            control={
              <Checkbox
                checked={filters.serviceOffering.windowCleaning}
                onChange={(e) =>
                  handleCheckboxChange(
                    'serviceOffering',
                    'windowCleaning',
                    e.target.checked
                  )
                }
              />
            }
            label="Window Cleaning"
          />
          <FormControlLabel
            control={
              <Checkbox
                checked={filters.serviceOffering.carValet}
                onChange={(e) =>
                  handleCheckboxChange(
                    'serviceOffering',
                    'carValet',
                    e.target.checked
                  )
                }
              />
            }
            label="Car Valet"
          />
        </FormGroup>
      </Box>
      <Box sx={{ display: 'flex', gap: 1, flexDirection: 'column' }}>
        <Button
          variant="contained"
          onClick={onApplyFilters}
          sx={{
            width: '100%',
            backgroundColor: '#1976d2',
            '&:hover': {
              backgroundColor: '#1565c0',
            },
          }}
        >
          Apply Filters
        </Button>
        <Button
          variant="outlined"
          onClick={onClearFilters}
          sx={{
            width: '100%',
            borderColor: '#1976d2',
            color: '#1976d2',
            '&:hover': {
              borderColor: '#1565c0',
              backgroundColor: 'rgba(25, 118, 210, 0.04)',
            },
          }}
        >
          Clear Filters
        </Button>
      </Box>
    </Box>
  );
};

export default Sidebar;
