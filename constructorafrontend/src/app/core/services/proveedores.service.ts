import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ProveedoresService {

  private apiUrl = 'http://localhost:3000/api/v1/Proveedors'; // URL de tu backend
  

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken(); // Obtener el token desde AuthService
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  getProveedores(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl, { headers: this.getHeaders() });
  }

  getProveedor(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }

  createProveedor(proveedor: any): Observable<any> {
    return this.http.post(this.apiUrl, proveedor, { headers: this.getHeaders() });
  }

  updateProveedor(proveedor: any): Observable<any> {
    return this.http.put(this.apiUrl, proveedor, { headers: this.getHeaders() });
  }

  deleteProveedor(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }
}

