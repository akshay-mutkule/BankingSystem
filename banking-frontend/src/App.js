import React, { useState } from 'react';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import Login from './components/Login';
import Dashboard from './components/Dashboard';

const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2',
    },
    secondary: {
      main: '#dc004e',
    },
  },
});

function App() {
  const [loggedIn, setLoggedIn] = useState(false);
  const [accNo, setAccNo] = useState('');

  const handleLogin = (accountNumber) => {
    setAccNo(accountNumber);
    setLoggedIn(true);
  };

  const handleLogout = () => {
    setLoggedIn(false);
    setAccNo('');
  };

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      {!loggedIn ? (
        <Login onLogin={handleLogin} />
      ) : (
        <Dashboard accNo={accNo} onLogout={handleLogout} />
      )}
    </ThemeProvider>
  );
}

export default App;