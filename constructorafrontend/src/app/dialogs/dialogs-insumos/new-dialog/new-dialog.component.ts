import { Component, Inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { InsumosService } from '../../../core/services/insumos.service';
import { ToastrService } from 'ngx-toastr';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-new-dialog',
  imports: [CommonModule,FormsModule],
  templateUrl: './new-dialog.component.html',
  styleUrl: './new-dialog.component.css'
})
export class NewDialogComponent {
  insumo = {
    nombreInsumo: '',
    descripcion: '',
    tipo: ''
  };

  constructor(public dialogRef: MatDialogRef<NewDialogComponent>,
    private insumosService: InsumosService,
    private toastr: ToastrService,
    @Inject(MAT_DIALOG_DATA) public data: any // Esto debe estar bien definido
  ) {}
    // Inicializamos los datos si no están presentes
  ngOnInit(): void {
  }

  validateInsumo(): boolean {
    if (!this.insumo.nombreInsumo || this.insumo.nombreInsumo.length > 45) {
      this.toastr.error('El nombre del Insumo es obligatorio y no debe exceder los 45 caracteres.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (!this.insumo.descripcion || this.insumo.descripcion.length > 45) {
      this.toastr.error('La descripcion del Insumo es obligatorio y no debe exceder los 45 caracteres.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (!this.insumo.tipo || this.insumo.tipo.length > 20) {
      this.toastr.error('El estado es obligatorio y no debe exceder los 20 caracteres.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }    
    return true;
  }

  onSave(): void {
    if (!this.validateInsumo()) {
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
  
    this.insumosService.createInsumo(this.insumo).subscribe({
      next: (response) => {
        this.toastr.success('Insumo creado exitosamente.', 'Éxito', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
        this.dialogRef.close(response);
      },
      error: (err) => {
        console.error('Error al crear Insumo:', err);
        this.toastr.error('Error al crear Insumo.', 'Error', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
      }
    });
  }

  onCancel(): void {
    console.log(this.insumo)
    this.dialogRef.close();
  }
}