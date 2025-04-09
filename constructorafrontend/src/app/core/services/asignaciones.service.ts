import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AsignacionesService {
  private apiUrl = 'http://localhost:3000/api/v1/AsignacionMaestros';

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken(); // Obtener el token desde AuthService
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  getAsignaciones(id: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/proyecto/${id}`, { headers: this.getHeaders() });
  }

  getAsignacion(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }

  createAsignacion(Asignacion: any): Observable<any> {
    return this.http.post(this.apiUrl, Asignacion, { headers: this.getHeaders() });
  }

  updateAsignacion(Asignacion: any): Observable<any> {
    return this.http.put(this.apiUrl, Asignacion, { headers: this.getHeaders() });
  }

  deleteAsignacion(id1: any, id2: any): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id1}/${id2}`, { headers: this.getHeaders() });
  }
}
