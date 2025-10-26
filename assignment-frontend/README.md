# GLER Admin Panel Frontend

A React TypeScript application built with Material-UI that implements an admin panel interface for managing user waitlists.

## Features

- **Responsive Design**: Clean, modern interface matching the provided design with mobile support
- **User Management**: View and filter service providers and customers
- **Advanced Filtering**: Filter by postcode, registration status, date range, vendor type, and service offering
- **Live Search**: Real-time search through user data with automatic filtering
- **Data Table**: Fully functional table with:
  - All required columns (Email, Phone Number, Postcode, Vendor Type, Service Offering, Signup Date, Status, Actions)
  - Sorting on all columns
  - Pagination (10 rows per page, minimum 5 pages)
  - Row selection with "Select All" functionality
  - Horizontal scrolling on mobile devices
- **User Details Modal**: Complete user information modal with:
  - Company information display
  - Contact information fields
  - Customer details
  - Editable internal notes
  - Onboard/Reject action buttons
- **Filter Controls**: Apply Filters and Clear Filters buttons
- **TypeScript**: Full type safety throughout the application

## Tech Stack

- **React 19** with TypeScript
- **Material-UI (MUI)** for UI components
- **React Router** for navigation
- **Vite** for build tooling

## Project Structure

```
src/
├── components/          # Reusable UI components
│   ├── Header.tsx      # Top navigation bar
│   ├── Sidebar.tsx     # Left sidebar with filters
│   ├── DataTable.tsx   # Data table component
│   └── UserDetailsModal.tsx # User details modal
├── pages/               # Page components
│   └── WaitlistPage.tsx # Main waitlist page
├── interfaces/          # TypeScript type definitions
│   └── User.ts         # User and filter interfaces
├── shared/             # Shared utilities and components
├── utils/              # Helper functions
└── App.tsx             # Main application component
```

## Getting Started

1. Install dependencies:
   ```bash
   npm install
   ```

2. Start the development server:
   ```bash
   npm run dev
   ```

3. Open your browser and navigate to `http://localhost:5173`

## Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run preview` - Preview production build
- `npm run lint` - Run ESLint

## Components

### Header
- Navigation links (Service Dashboard, Finance Forecast, Human Resources, Users, Compliances & Verification)
- Notification and chat icons
- User profile display

### Sidebar
- Filter options for:
  - Postcode search
  - Registration status (Onboarded/Rejected)
  - Date range selection
  - Vendor type (Independent/Company)
  - Service offering (Housekeeping/Window Cleaning/Car Valet)

### DataTable
- Tabbed interface (Service Providers/Customers)
- Live search functionality with automatic filtering
- Sortable columns (click column headers to sort)
- Row selection with individual checkboxes and "Select All" option
- Pagination with 10 rows per page
- Status indicators with color coding (green for Onboarded, red for Rejected)
- Edit actions that open the User Details modal
- Horizontal scrolling on mobile devices

### UserDetailsModal
- Complete user information display
- Company information section with status chips
- Contact information fields (Email, Phone, Location, Signup Date)
- Customer details (Type, Service Offerings)
- Editable internal notes section
- Action buttons (Onboard/Reject) that update user status
- Responsive design that works on all screen sizes

## Data Model

The application uses mock data that includes:
- User email and phone number
- Postcode and vendor type
- Service offering and signup date
- Registration status (Onboarded/Rejected/Pending)

## Styling

The application uses Material-UI's theming system with:
- Custom color palette
- Consistent typography
- Responsive design principles
- Clean, professional appearance matching the provided design