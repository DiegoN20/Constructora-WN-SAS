import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ProveedoresService } from '../../../core/services/proveedores.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-delete-dialog',
  imports: [],
  templateUrl: './delete-dialog.component.html',
  styleUrl: './delete-dialog.component.css'
})
export class DeleteDialogComponent {
  constructor(public dialogRef: MatDialogRef<DeleteDialogComponent>,
        private proveedoresService: ProveedoresService,
        private toastr: ToastrService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}
  
  // Método para cerrar el diálogo sin eliminar
  onCancel(): void {
    this.dialogRef.close(); // Retorna 'false' para indicar que no se realizó la acción
  }

  // Método para la eliminación
  onDelete(): void {
    const token = localStorage.getItem('authToken');
    if (!token) {
      this.toastr.error('Sesión no válida. Por favor, inicia sesión nuevamente.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      this.dialogRef.close(false);
      return;
    }
  
    this.proveedoresService.deleteProveedor(this.data.idProveedores).subscribe({
      next: (response) => {
        this.toastr.success('Proveedor eliminado exitosamente.', 'Éxito', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
        this.dialogRef.close(true);
      },
      error: (err) => {
        console.error('Error al eliminar proveedor:', err);
        this.toastr.error('Error al eliminar proveedor.', 'Error', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
        this.dialogRef.close(false);
      }
    });
  }
}

