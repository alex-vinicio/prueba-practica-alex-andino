export interface Client {
  identifier: string;
  name: string;
  gender: 'HOMBRE' | 'MUJER';
  age: number;
  address: string;
  phone: string;
  password: string;
  email: string;
  status: 'ACTIVO' | 'INACTIVO';
}

export interface Account {
  accountNumber: string;
  accountType: 'AHORRO' | 'CORRIENTE';
  initialBalance: string;
  status: 'ACTIVO' | 'INACTIVO';
  clientName: string;
}

export interface Movement {
  date: string;
  client: string;
  accountNumber: string;
  movementType: 'CREDIT' | 'DEBIT';
  accountType: 'AHORRO' | 'CORRIENTE';
  initialBalance: number;
  status: 'ACTIVO' | 'INACTIVO';
  movement: number;
  availableBalance: number;
}

export interface ErrorResponse {
  error: string;
  message: string;
}

export interface CreateMovementRequest {
  movementType: 'CREDIT' | 'DEBIT';
  value: string;
  accountNumber: string;
}