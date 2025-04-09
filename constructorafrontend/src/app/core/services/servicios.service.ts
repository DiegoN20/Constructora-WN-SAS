import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ServiciosService {
  private apiUrl = 'http://localhost:3000/api/v1/ServicioExternos';

  constructor(private http: HttpClient, private authService: AuthService) { }
  
  
  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken(); // Obtener el token desde AuthService
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  getServicios(id: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/proyecto/${id}`, { headers: this.getHeaders() });
  }

  getServicio(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }

  createServicio(Servicio: any): Observable<any> {
    return this.http.post(this.apiUrl, Servicio, { headers: this.getHeaders() });
  }

  updateServicio(Servicio: any): Observable<any> {
    return this.http.put(this.apiUrl, Servicio, { headers: this.getHeaders() });
  }

  deleteServicio(id1: any, id2: any): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id1}/${id2}`, { headers: this.getHeaders() });
  }
}
