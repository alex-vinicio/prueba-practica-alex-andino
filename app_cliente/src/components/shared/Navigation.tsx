import { AppBar, Toolbar, Typography, Button, Box } from '@mui/material'
import { Link as RouterLink } from 'react-router-dom'

function Navigation() {
  return (
    <AppBar position="static">
      <Toolbar>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          Sistema Bancario
        </Typography>
        <Box sx={{ display: 'flex', gap: 2 }}>
          <Button color="inherit" component={RouterLink} to="/clients">
            Clientes
          </Button>
          <Button color="inherit" component={RouterLink} to="/accounts">
            Cuentas
          </Button>
          <Button color="inherit" component={RouterLink} to="/movements">
            Movimientos
          </Button>
        </Box>
      </Toolbar>
    </AppBar>
  )
}

export default Navigation