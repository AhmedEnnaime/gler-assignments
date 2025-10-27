import React, { useState } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  IconButton,
  Typography,
  Box,
  Chip,
  TextField,
  Button,
  Divider,
} from '@mui/material';
import { Close as CloseIcon, Edit as EditIcon } from '@mui/icons-material';
import type { User } from '../interfaces/User';

interface UserDetailsModalProps {
  open: boolean;
  onClose: () => void;
  user: User | null;
  onUpdateUser: (user: User) => void;
}

const UserDetailsModal: React.FC<UserDetailsModalProps> = ({
  open,
  onClose,
  user,
  onUpdateUser,
}) => {
  const [notes, setNotes] = useState('No Note Added yet');
  const [isEditingNotes, setIsEditingNotes] = useState(false);

  if (!user) return null;

  const handleOnboard = () => {
    const updatedUser = { ...user, status: 'Onboarded' as const };
    onUpdateUser(updatedUser);
    onClose();
  };

  const handleReject = () => {
    const updatedUser = { ...user, status: 'Rejected' as const };
    onUpdateUser(updatedUser);
    onClose();
  };

  const handleEditNotes = () => {
    setIsEditingNotes(true);
  };

  const handleSaveNotes = () => {
    setIsEditingNotes(false);
  };

  return (
    <Dialog
      open={open}
      onClose={onClose}
      maxWidth="md"
      fullWidth
      slotProps={{
        paper: {
          sx: {
            borderRadius: 2,
            minHeight: '600px',
          },
        },
      }}
    >
      <DialogTitle sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', pb: 1 }}>
        <Typography variant="h5" sx={{ fontWeight: 'bold' }}>
          User Details
        </Typography>
        <IconButton onClick={onClose} size="small">
          <CloseIcon />
        </IconButton>
      </DialogTitle>

      <DialogContent sx={{ px: 3, py: 2 }}>
        <Box sx={{ mb: 3 }}>
          <Typography variant="h6" sx={{ fontWeight: 'bold', mb: 1 }}>
            CleanPro Solutions
          </Typography>
          <Typography variant="body2" sx={{ color: 'text.secondary', mb: 2 }}>
            contact@cleanpro.com
          </Typography>
          <Box sx={{ display: 'flex', gap: 1 }}>
            <Chip label="Customer" color="primary" size="small" />
            <Chip label="invited" color="secondary" size="small" />
          </Box>
        </Box>

        <Divider sx={{ mb: 3 }} />
        <Box sx={{ mb: 3 }}>
          <Typography variant="h6" sx={{ fontWeight: 'bold', mb: 2 }}>
            Contact Information
          </Typography>
          <Box sx={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 2 }}>
            <TextField
              label="Email"
              value={user.email}
              variant="outlined"
              size="small"
              disabled
              sx={{ gridColumn: '1 / -1' }}
            />
            <TextField
              label="Phone Number"
              value={user.phoneNumber}
              variant="outlined"
              size="small"
              disabled
            />
            <TextField
              label="Location"
              value="United Kingdom"
              variant="outlined"
              size="small"
              disabled
            />
            <TextField
              label="Signed up date"
              value={`Signed up ${user.signupDate}`}
              variant="outlined"
              size="small"
              disabled
              sx={{ gridColumn: '1 / -1' }}
            />
          </Box>
        </Box>
        <Box sx={{ mb: 3 }}>
          <Typography variant="h6" sx={{ fontWeight: 'bold', mb: 2 }}>
            Customer Details
          </Typography>
          <Box sx={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 2 }}>
            <TextField
              label="Type"
              value="individual"
              variant="outlined"
              size="small"
              disabled
            />
            <TextField
              label="User Details"
              value={user.serviceOffering.toLowerCase()}
              variant="outlined"
              size="small"
              disabled
            />
          </Box>
        </Box>
        <Box sx={{ mb: 3 }}>
          <Typography variant="h6" sx={{ fontWeight: 'bold', mb: 2 }}>
            Internal Notes
          </Typography>
          <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
            {isEditingNotes ? (
              <>
                <TextField
                  multiline
                  rows={3}
                  value={notes}
                  onChange={(e) => setNotes(e.target.value)}
                  variant="outlined"
                  size="small"
                  sx={{ flex: 1 }}
                />
                <Button
                  variant="contained"
                  size="small"
                  onClick={handleSaveNotes}
                >
                  Save
                </Button>
              </>
            ) : (
              <>
                <Typography variant="body2" sx={{ flex: 1 }}>
                  {notes}
                </Typography>
                <IconButton size="small" onClick={handleEditNotes}>
                  <EditIcon />
                </IconButton>
              </>
            )}
          </Box>
        </Box>
      </DialogContent>

      <DialogActions sx={{ px: 3, pb: 3, gap: 2 }}>
        <Button
          variant="contained"
          color="success"
          onClick={handleOnboard}
          sx={{
            backgroundColor: '#4caf50',
            '&:hover': {
              backgroundColor: '#45a049',
            },
          }}
        >
          Onboard
        </Button>
        <Button
          variant="contained"
          color="error"
          onClick={handleReject}
          sx={{
            backgroundColor: '#f44336',
            '&:hover': {
              backgroundColor: '#da190b',
            },
          }}
        >
          Reject
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default UserDetailsModal;
