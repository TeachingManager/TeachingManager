import React from 'react';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Button from '@mui/material/Button';
import './studenttoolbar.css';
import SearchIcon from '@mui/icons-material/Search';



// Create a custom theme
const theme = createTheme({
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          backgroundColor: '#0895D6', // Change this to your desired color
          color: '#fff',
          fontFamily: 'Arial, sans-serif', // Change this to your desired font
          '&:hover': {
            backgroundColor: '#45A049', // Change this to your desired hover color
          },
        },
      },
    },
  },
});

export default function StudentToolbar() {
  return (
    <ThemeProvider theme={theme}>
      <div className='toolbar-container'>
        <Button variant="contained" size="small"> + 학생 추가하기</Button>
        
        <Button variant="outlined" size="small" startIcon={<SearchIcon/>} >학생 찾기</Button>
      </div>
    </ThemeProvider>
  );
}
