import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProyectosService {
  private apiUrl = 'http://localhost:3000/api/v1/Proyectos'; // URL de tu backend
  
  constructor(private http: HttpClient, private authService: AuthService) {}
  
  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken(); // Obtener el token desde AuthService
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  getProyectos(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl, { headers: this.getHeaders() });
  }

  getProyecto(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }

  createProyecto(Proyecto: any): Observable<any> {
    return this.http.post(this.apiUrl, Proyecto, { headers: this.getHeaders() });
  }

  updateProyecto(Proyecto: any): Observable<any> {
    return this.http.put(this.apiUrl, Proyecto, { headers: this.getHeaders() });
  }

  deleteProyecto(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }
}
