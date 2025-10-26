import React from 'react';
import {
  AppBar,
  Toolbar,
  Typography,
  Box,
  IconButton,
  Avatar,
  useMediaQuery,
  useTheme,
} from '@mui/material';
import {
  Notifications as NotificationsIcon,
  Chat as ChatIcon,
} from '@mui/icons-material';

const Header: React.FC = () => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('md'));

  return (
    <AppBar position="static" sx={{ backgroundColor: '#f8f9fa', boxShadow: 'none', borderBottom: '1px solid #e9ecef' }}>
      <Toolbar sx={{ justifyContent: 'space-between', px: { xs: 2, md: 3 } }}>
        <Box sx={{ display: { xs: 'none', md: 'flex' }, gap: 3 }}>
          <Typography
            variant="body1"
            sx={{
              color: 'text.primary',
              cursor: 'pointer',
              '&:hover': { color: 'primary.main' },
            }}
          >
            Service Dashboard
          </Typography>
          <Typography
            variant="body1"
            sx={{
              color: 'text.primary',
              cursor: 'pointer',
              '&:hover': { color: 'primary.main' },
            }}
          >
            Finance Forecast
          </Typography>
          <Typography
            variant="body1"
            sx={{
              color: 'primary.main',
              fontWeight: 'bold',
              cursor: 'pointer',
            }}
          >
            Human Resources
          </Typography>
          <Typography
            variant="body1"
            sx={{
              color: 'text.primary',
              cursor: 'pointer',
              '&:hover': { color: 'primary.main' },
            }}
          >
            Users
          </Typography>
          <Typography
            variant="body1"
            sx={{
              color: 'text.primary',
              cursor: 'pointer',
              '&:hover': { color: 'primary.main' },
            }}
          >
            Compliances & Verification
          </Typography>
        </Box>
        {isMobile && (
          <Box sx={{ display: 'flex', alignItems: 'center' }}>
            <Typography
              variant="h6"
              sx={{
                color: 'primary.main',
                fontWeight: 'bold',
                fontSize: '1.1rem',
              }}
            >
              Human Resources
            </Typography>
          </Box>
        )}
        <Box sx={{ display: 'flex', alignItems: 'center', gap: { xs: 1, md: 2 } }}>
          <IconButton size={isMobile ? 'small' : 'medium'}>
            <NotificationsIcon />
          </IconButton>
          <IconButton size={isMobile ? 'small' : 'medium'}>
            <ChatIcon />
          </IconButton>
          <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
            <Avatar sx={{ width: { xs: 28, md: 32 }, height: { xs: 28, md: 32 }, backgroundColor: '#1976d2' }}>MS</Avatar>
            <Box sx={{ display: { xs: 'none', sm: 'block' } }}>
              <Typography variant="body2" sx={{ fontWeight: 'bold', color: 'text.primary', fontSize: { xs: '0.75rem', md: '0.875rem' } }}>
                Max Smith
              </Typography>
              <Typography variant="caption" sx={{ color: 'text.secondary', fontSize: { xs: '0.7rem', md: '0.75rem' } }}>
                London, UK
              </Typography>
            </Box>
          </Box>
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Header;
