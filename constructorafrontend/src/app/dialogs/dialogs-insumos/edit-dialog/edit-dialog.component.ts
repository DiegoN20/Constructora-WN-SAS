import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { InsumosService } from '../../../core/services/insumos.service';

@Component({
  selector: 'app-edit-dialog-component',
  imports: [CommonModule, FormsModule],
  templateUrl: './edit-dialog.component.html',
  styleUrl: './edit-dialog.component.css'
})
export class EditDialogComponent {
  insumo = {
    nombreInsumo: '',
    descripcion: '',
    tipo: ''
  };

  constructor(
    public dialogRef: MatDialogRef<EditDialogComponent>,
    private insumosService: InsumosService,
    private toastr: ToastrService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
    if (this.data) {
      this.insumo = { ...this.data };
    }
  }

  
  onCancel(): void {
    this.dialogRef.close();
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

    console.log(this.insumo)
  
    this.insumosService.updateInsumo(this.insumo).subscribe({
      next: (response) => {
        this.toastr.success('Insumo modificado exitosamente.', 'Éxito', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
        this.dialogRef.close(response);
      },
      error: (err) => {
        console.error('Error al modificar Insumo:', err);
        this.toastr.error('Error al modificar Insumo.', 'Error', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
      }
    });
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

}
