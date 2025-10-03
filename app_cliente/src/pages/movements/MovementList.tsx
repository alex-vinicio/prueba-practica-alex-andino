import { useState } from 'react';
import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Button,
  Typography,
  Box,
  Alert,
} from '@mui/material';
import { useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { getMovements } from '../../services/api';
import type { Movement } from '../../types';

function MovementList() {
  const navigate = useNavigate();
  const [error, setError] = useState<string | null>(null);

  const { data: movements, isLoading, error: queryError } = useQuery<Movement[]>({
    queryKey: ['movements'],
    queryFn: getMovements,
    onError: (err) => {
      if (err instanceof Error) {
        setError(err.message);
      } else {
        setError('Error desconocido al cargar los movimientos');
      }
    }
  });

  if (isLoading) return <Typography>Cargando...</Typography>;
  
  if (queryError) return <Alert severity="error">Error al cargar los movimientos</Alert>;

  return (
    <div>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 3 }}>
        <Typography variant="h4">Movimientos</Typography>
        <Button
          variant="contained"
          color="primary"
          onClick={() => navigate('/movements/new')}
        >
          Nuevo Movimiento
        </Button>
      </Box>

      {error && (
        <Alert severity="error" sx={{ mb: 2 }}>
          {error}
        </Alert>
      )}

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Fecha</TableCell>
              <TableCell>Cliente</TableCell>
              <TableCell>Número de Cuenta</TableCell>
              <TableCell>Tipo</TableCell>
              <TableCell>Saldo Inicial</TableCell>
              <TableCell>Movimiento</TableCell>
              <TableCell>Saldo Disponible</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {movements && movements.map((movement: Movement, index: number) => (
              <TableRow key={index}>
                <TableCell>{movement.date}</TableCell>
                <TableCell>{movement.client}</TableCell>
                <TableCell>{movement.accountNumber}</TableCell>
                <TableCell>{movement.movementType}</TableCell>
                <TableCell>${movement.initialBalance.toFixed(2)}</TableCell>
                <TableCell>${movement.movement.toFixed(2)}</TableCell>
                <TableCell>${movement.availableBalance.toFixed(2)}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
}

export default MovementList;