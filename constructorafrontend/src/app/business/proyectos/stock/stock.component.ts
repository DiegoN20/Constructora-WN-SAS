import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { StockService } from '../../../core/services/stock.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-stock',
  imports: [CommonModule, MatDialogModule],
  templateUrl: './stock.component.html',
  styleUrl: './stock.component.css'
})
export class StockComponent implements OnInit{
  stocks: any[] = [];
  currentPage: number = 1;
  pageSize: number = 10; // Número de Asignaciones por página
  paginatedStocks: any[] = [];
  Math = Math;
  proyectoId!: number;

  constructor(private stockService: StockService,
      public dialog: MatDialog,
      private route: ActivatedRoute){}

  ngOnInit(): void {
    this.proyectoId = +this.route.snapshot.paramMap.get('id')!;
    this.getStocks();
  }
  
  getPaginatedStocks(): void {
    if (this.stocks.length > 0) {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = Math.min(startIndex + this.pageSize, this.stocks.length); // Ajuste de límites
      this.paginatedStocks = this.stocks.slice(startIndex, endIndex);
    } else {
      this.paginatedStocks = [];
    }
  }
  
  changePage(newPage: number): void {
    if (newPage > 0 && newPage <= Math.ceil(this.stocks.length / this.pageSize)) {
      this.currentPage = newPage;
      console.log('Página actual:', this.currentPage); // Registra la página actual
      this.getPaginatedStocks();
      console.log('Stocks paginados:', this.paginatedStocks); // Verifica los datos
    }
  }

  getStocks(): void {
    this.stockService.getStock(this.proyectoId).subscribe(
      (data) => {
        this.stocks = data;
        this.getPaginatedStocks();
      },
      (error) => {
        console.error('Error al obtener Stocks:', error);
      }
    );
  }

}

