import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InsumosService {
  private apiUrl = 'http://localhost:3000/api/v1/Insumos'; // URL de tu backend
  
  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken(); // Obtener el token desde AuthService
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  getInsumos(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl, { headers: this.getHeaders() });
  }

  getInsumo(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }

  createInsumo(insumo: any): Observable<any> {
    return this.http.post(this.apiUrl, insumo, { headers: this.getHeaders() });
  }

  updateInsumo(insumo: any): Observable<any> {
    return this.http.put(this.apiUrl, insumo, { headers: this.getHeaders() });
  }

  deleteInsumo(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }

}
