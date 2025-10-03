import { Routes, Route } from 'react-router-dom'
import { Box, Container } from '@mui/material'
import Navigation from './components/shared/Navigation'
import ClientList from './pages/clients/ClientList'
import ClientForm from './pages/clients/ClientForm'
import AccountList from './pages/accounts/AccountList'
import AccountForm from './pages/accounts/AccountForm'
import MovementList from './pages/movements/MovementList'
import MovementForm from './pages/movements/MovementForm'

function App() {
  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
      <Navigation />
      <Container component="main" sx={{ mt: 4, mb: 4, flex: 1 }}>
        <Routes>
          <Route path="/" element={<ClientList />} />
          <Route path="/clients" element={<ClientList />} />
          <Route path="/clients/new" element={<ClientForm />} />
          <Route path="/clients/edit/:id" element={<ClientForm />} />
          <Route path="/accounts" element={<AccountList />} />
          <Route path="/accounts/new" element={<AccountForm />} />
          <Route path="/accounts/edit/:id" element={<AccountForm />} />
          <Route path="/movements" element={<MovementList />} />
          <Route path="/movements/new" element={<MovementForm />} />
        </Routes>
      </Container>
    </Box>
  )
}

export default App