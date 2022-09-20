import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { UserDetail } from '../classes/user-detail';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  // Base URL
  private readonly baseUrl = "http://localhost:8080/api";
  private readonly rootUrl = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  getPreLogin(userDetail: UserDetail): Observable<HttpResponse<any>> {
    const body = {
      username: userDetail.username,
      password: userDetail.password
    }
    return this.http.post<any>(`${this.baseUrl}`+"/auth/login", body, {observe: 'response'})
    .pipe(catchError(this.handleError));
  }

  login(url: String, accessToken: String): Observable<any>{
    const headers = { 
      'Authorization': 'Bearer ' + accessToken
    };
    const body = {
      "accessToken": accessToken
    };
    return this.http.post(`${this.rootUrl}` + url, body, {observe: 'response', responseType: 'text', headers: headers})
  }

  logout(): Observable<any>{
    const accessToken = localStorage.getItem('access-token');
    localStorage.removeItem('isUserLoggedIn');
    localStorage.removeItem('access-token');
    localStorage.removeItem('refresh-token');
    console.log("show token get from localStorage: "+accessToken);
    const headers = { 
      'Authorization': 'Bearer ' + accessToken
    };
    return this.http.post(`${this.rootUrl}` + "/api/users/logout", "", {observe: 'response', responseType: 'text', headers: headers});
  }

  test(): Observable<any>{
    localStorage.removeItem('isUserLoggedIn');
    return this.http.get("http://localhost:8080/api/auth/re");
  }
  handleError(error: HttpErrorResponse) {
    return throwError(() => new Error(error.error));
}
}
