import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ProveedoresService } from '../../../core/services/proveedores.service';
import { ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-edit-dialog-component',
  imports: [CommonModule, FormsModule],
  templateUrl: './edit-dialog.component.html',
  styleUrl: './edit-dialog.component.css'
})
export class EditDialogComponent {
  proveedor = {
    nombreProveedor: '',
    tipoServicio: '',
    correo: '',
    telefono: null
  };

  constructor(
    public dialogRef: MatDialogRef<EditDialogComponent>,
    private proveedoresService: ProveedoresService,
    private toastr: ToastrService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
    if (this.data) {
      this.proveedor = { ...this.data };
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  onSave(): void {
    if (!this.validateProveedor()) {
      return;
    }
  
    const token = localStorage.getItem('authToken');
    if (!token) {
      this.toastr.error('Sesión no válida. Por favor, inicia sesión nuevamente.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      this.dialogRef.close();
      return;
    }
  
    this.proveedoresService.updateProveedor(this.proveedor).subscribe({
      next: (response) => {
        this.toastr.success('Proveedor modificado exitosamente.', 'Éxito', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
        this.dialogRef.close(response);
      },
      error: (err) => {
        console.error('Error al modificado proveedor:', err);
        this.toastr.error('Error al modificado proveedor.', 'Error', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
      }
    });
  }
  

  validateProveedor(): boolean {
    if (!this.proveedor.nombreProveedor || this.proveedor.nombreProveedor.length > 45) {
      this.toastr.error('El nombre del proveedor es obligatorio y no debe exceder los 45 caracteres.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (!this.proveedor.tipoServicio || this.proveedor.tipoServicio.length > 45) {
      this.toastr.error('El tipo de servicio es obligatorio y no debe exceder los 45 caracteres.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (this.proveedor.correo && (this.proveedor.correo.length > 45 || !this.proveedor.correo.includes('@'))) {
      this.toastr.error('El correo electrónico no es válido.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (this.proveedor.telefono && !this.isTelefonoValido(this.proveedor.telefono)) {
      this.toastr.error('El número de teléfono debe ser válido y tener hasta 10 dígitos.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    return true;
  }

  isTelefonoValido(telefono: number | null): boolean {
    if (!telefono) {
      return true; // Si no hay teléfono, lo consideramos válido
    }
    const telefonoStr = telefono.toString(); // Convertimos el número a string
    return /^[0-9]{1,10}$/.test(telefonoStr); // Validamos con la regex
  }

  isCorreoValido(correo: string): boolean {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(correo); // Valida correo estándar
  }
}
