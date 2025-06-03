import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MaestrosService {
  private apiUrl = 'http://localhost:3000/api/v1/Maestros'; // URL de tu backend
  

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken(); // Obtener el token desde AuthService
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  getMaestros(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl, { headers: this.getHeaders() });
  }

  getMaestro(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }

  getMaestrosDisponibles(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/disponibles`, { headers: this.getHeaders() });
  }

  createMaestro(Maestro: any): Observable<any> {
    return this.http.post(this.apiUrl, Maestro, { headers: this.getHeaders() });
  }

  updateMaestro(Maestro: any): Observable<any> {
    return this.http.put(this.apiUrl, Maestro, { headers: this.getHeaders() });
  }

  deleteMaestro(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }
}
