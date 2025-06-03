import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class StockService {
private apiUrl = 'http://localhost:3000/api/v1/Stocks';

  constructor(private http: HttpClient, private authService: AuthService) { }

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken(); // Obtener el token desde AuthService
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  getStocks(id: number): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl, { headers: this.getHeaders() });
  }

  getStock(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/proyecto/${id}`, { headers: this.getHeaders() });
  }

  createStock(Stock: any): Observable<any> {
    return this.http.post(this.apiUrl, Stock, { headers: this.getHeaders() });
  }

  updateStock(Stock: any): Observable<any> {
    return this.http.put(this.apiUrl, Stock, { headers: this.getHeaders() });
  }

  deleteStock(id: any): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }
}