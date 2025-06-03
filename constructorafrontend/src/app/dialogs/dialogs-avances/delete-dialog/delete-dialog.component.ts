import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { AvancesService } from '../../../core/services/avances.service';

@Component({
  selector: 'app-delete-dialog',
  imports: [],
  templateUrl: './delete-dialog.component.html',
  styleUrl: './delete-dialog.component.css'
})
export class DeleteDialogComponent {
  constructor(public dialogRef: MatDialogRef<DeleteDialogComponent>,
        private avancesService: AvancesService,
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
  
    this.avancesService.deleteAvance(this.data.idAvance).subscribe({
      next: (response) => {
        this.toastr.success('Avance eliminado exitosamente.', 'Éxito', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
        this.dialogRef.close(true);
      },
      error: (err) => {
        console.error('Error al eliminar Avance:', err);
        this.toastr.error('Error al eliminar Avance.', 'Error', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
        this.dialogRef.close(false);
      }
    });
  }
}

