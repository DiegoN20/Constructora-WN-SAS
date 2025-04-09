import { Component, Inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { CommonModule } from '@angular/common';
import { InsumosService } from '../../../core/services/insumos.service';
import { AvancesService } from '../../../core/services/avances.service';

@Component({
  selector: 'app-new-dialog',
  imports: [CommonModule,FormsModule],
  templateUrl: './new-dialog.component.html',
  styleUrl: './new-dialog.component.css'
})
export class NewDialogComponent {
  proyectoId!: number;
  insumos: any[] = [];
  avance = {
    proyecto: {
      idProyectos: null as number | null
    },
    insumo: {
      idInsumos: null as number | null
    },
    numeroPiso: null,
    cantidadComprada: null,
    costoInsumos: null,
    fechaCompra: '',
    cantidadUsada: null
  };

  constructor(public dialogRef: MatDialogRef<NewDialogComponent>,
    private avancesService: AvancesService,
    private insumosService: InsumosService,
    private toastr: ToastrService,
    @Inject(MAT_DIALOG_DATA) public data: any // Esto debe estar bien definido
  ) {}
    // Inicializamos los datos si no están presentes
  ngOnInit(): void {
    this.cargarInsumos();
    this.proyectoId = this.data.proyectoId;
    this.avance.proyecto.idProyectos = this.proyectoId;
  }

  cargarInsumos(): void {
    this.insumosService.getInsumos().subscribe({
      next: (data) => {
        this.insumos = data; // Almacena los datos recibidos
      },
      error: (err) => {
        console.error('Error al obtener maestros disponibles:', err);
        this.toastr.error('Error al cargar los maestros disponibles.', 'Error', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
      }
    });
  }

  validateAvance(): boolean {
    if (!this.avance.numeroPiso && !this.isValorPositivo(this.avance.numeroPiso)) {
      this.toastr.error('La unidad no es válido.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (!this.avance.cantidadComprada || !this.isValorPositivo(this.avance.cantidadComprada)) {
      this.toastr.error('La cantidad comprada es obligatoria y no debe ser negativa.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (!this.avance.costoInsumos || !this.isValorPositivo(this.avance.costoInsumos)) {
      this.toastr.error('El costo es obligatorio y no debe ser negativo.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (!this.isValorPositivo(this.avance.cantidadUsada)) {
      this.toastr.error('La cantidad usada no es válida.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (!this.avance.fechaCompra && !this.isFechaValida(this.avance.fechaCompra)) {
      this.toastr.error('La fecha de compra no es válida.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    return true;
  }

  isValorPositivo(numero: number | null): boolean {
    if (numero === null || !numero) {
        return true; // O maneja el caso como prefieras
    }
    return numero > 0;
  }

  isFechaValida(fecha: string): boolean {
    if (fecha === null || !fecha) {
      return true; // Si es null, es válido
    }
    const regex = /^\d{4}-\d{2}-\d{2}$/; // Formato AAAA-MM-DD
    return regex.test(fecha); // Verifica si la fecha es válida
  }

  onSave(): void {
    console.log(this.avance)
    if (!this.validateAvance()) {
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
  
    this.avancesService.createAvance(this.avance).subscribe({
      next: (response) => {
        this.toastr.success('Avance creado exitosamente.', 'Éxito', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
        this.dialogRef.close(response);
      },
      error: (err) => {
        console.error('Error al crear avance:', err);
        this.toastr.error('Error al crear avance.', 'Error', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
      }
    });
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}