import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AvancesService {

  private apiUrl = 'http://localhost:3000/api/v1/AvancePorPisos';
  
    constructor(private http: HttpClient, private authService: AuthService) { }
  
    private getHeaders(): HttpHeaders {
        const token = this.authService.getToken(); // Obtener el token desde AuthService
        return new HttpHeaders({
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        });
      }
    
      getAvances(id: number): Observable<any[]> {
        return this.http.get<any[]>(this.apiUrl, { headers: this.getHeaders() });
      }
    
      getAvance(id: number): Observable<any> {
        return this.http.get<any>(`${this.apiUrl}/proyecto/${id}`, { headers: this.getHeaders() });
      }
    
      createAvance(Avance: any): Observable<any> {
        return this.http.post(this.apiUrl, Avance, { headers: this.getHeaders() });
      }
    
      updateAvance(Avance: any): Observable<any> {
        return this.http.put(this.apiUrl, Avance, { headers: this.getHeaders() });
      }
    
      deleteAvance(id: any): Observable<any> {
        return this.http.delete(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
      }
}
