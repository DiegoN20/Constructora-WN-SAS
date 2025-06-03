import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InventariosService {
  private apiUrl = 'http://localhost:3000/api/v1/InventarioInicial';

  constructor(private http: HttpClient, private authService: AuthService) { }

  private getHeaders(): HttpHeaders {
      const token = this.authService.getToken(); // Obtener el token desde AuthService
      return new HttpHeaders({
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      });
    }
  
    getInventarios(id: number): Observable<any[]> {
      return this.http.get<any[]>(this.apiUrl, { headers: this.getHeaders() });
    }
  
    getInventario(id: number): Observable<any> {
      return this.http.get<any>(`${this.apiUrl}/proyecto/${id}`, { headers: this.getHeaders() });
    }
  
    createInventario(Inventario: any): Observable<any> {
      return this.http.post(this.apiUrl, Inventario, { headers: this.getHeaders() });
    }
  
    updateInventario(Inventario: any): Observable<any> {
      return this.http.put(this.apiUrl, Inventario, { headers: this.getHeaders() });
    }
  
    deleteInventario(id: any): Observable<any> {
      return this.http.delete(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
    }
}
