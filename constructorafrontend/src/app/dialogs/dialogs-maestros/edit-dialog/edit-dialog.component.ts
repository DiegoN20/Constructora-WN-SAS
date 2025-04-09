import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { MaestrosService } from '../../../core/services/maestros.service';

@Component({
  selector: 'app-edit-dialog-component',
  imports: [CommonModule, FormsModule],
  templateUrl: './edit-dialog.component.html',
  styleUrl: './edit-dialog.component.css'
})
export class EditDialogComponent {
  maestro = {
    cedula: null,
    nombre: '',
    apellido: '',
    telefono: null,
    estadoMaestro: '',
    salario: null,
    fechaVinculacion: '',
    fechaDesvinculacion: ''
  };

  constructor(
    public dialogRef: MatDialogRef<EditDialogComponent>,
    private maestrosService: MaestrosService,
    private toastr: ToastrService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
    if (this.data) {
      this.maestro = { ...this.data };
    }
  }

  onSave(): void {
    if (!this.validateMaestro()) {
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
  
    this.maestrosService.updateMaestro(this.maestro).subscribe({
      next: (response) => {
        this.toastr.success('Maestro modificado exitosamente.', 'Éxito', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
        this.dialogRef.close(response);
      },
      error: (err) => {
        console.error('Error al modificar Maestro:', err);
        this.toastr.error('Error al modificar Maestro.', 'Error', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
      }
    });
  }

  onCancel(): void {
    this.dialogRef.close();
  }
  

  validateMaestro(): boolean {
    if (!this.maestro.cedula){
      this.toastr.error('La cedula del Maestro es obligatoria.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }

    if (!this.maestro.nombre || this.maestro.nombre.length > 45) {
      this.toastr.error('El nombre del Maestro es obligatorio y no debe exceder los 45 caracteres.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (!this.maestro.apellido || this.maestro.apellido.length > 45) {
      this.toastr.error('El apellido del Maestro es obligatorio y no debe exceder los 45 caracteres.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (this.maestro.telefono && !this.isTelefonoValido(this.maestro.telefono)) {
      this.toastr.error('El número de teléfono debe ser válido y tener hasta 10 dígitos.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (!this.maestro.estadoMaestro || this.maestro.estadoMaestro.length > 20) {
      this.toastr.error('El estado es obligatorio y no debe exceder los 20 caracteres.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (this.maestro.salario && !this.isSalarioValido(this.maestro.salario)) {
      this.toastr.error('El salario no es válido.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (this.maestro.fechaVinculacion && !this.isFechaValida(this.maestro.fechaVinculacion)) {
      this.toastr.error('La fecha no es válida.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (this.maestro.fechaDesvinculacion < this.maestro.fechaVinculacion) {
      this.toastr.error('La fecha de vinculacion debe ser anterior a la fecha de desvinculación.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (this.maestro.fechaDesvinculacion && !this.isFechaValida(this.maestro.fechaDesvinculacion)) {
      this.toastr.error('La fecha no es válida.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    
    return true;
  }

  isFechaValida(fecha: string): boolean {
    const regex = /^\d{4}-\d{2}-\d{2}$/; // Formato AAAA-MM-DD
    return regex.test(fecha); // Verifica si la fecha es válida
  }


  isTelefonoValido(telefono: number | null): boolean {
    if (!telefono) {
      return true; // Si no hay teléfono, lo consideramos válido
    }
    const telefonoStr = telefono.toString(); // Convertimos el número a string
    return /^[0-9]{1,10}$/.test(telefonoStr); // Validamos con la regex
  }

  isCedulaValida(cedula: number | null): boolean {
    if (cedula) {
      const cedulaStr = cedula.toString(); // Convertimos el número a string
      return /^[0-9]{1,10}$/.test(cedulaStr); // Si no hay teléfono, lo consideramos válido
    }else{
      return false;
    }
  }

  isSalarioValido(salario: number | null): boolean {
    if (salario) {
      salario > 0;
      return true;
    } else {
      return false;
    }
  }
}
