import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { Status } from '../enums/status.enum';
import { CustomResponse } from '../interfaces/custom-response';
import { Server } from '../interfaces/server';

@Injectable({
  providedIn: 'root'
})
export class ServerService {

  private readonly apiURL = 'http://localhost:8080'

  constructor(private http: HttpClient) { }

  // Version classique de créer le front
  /* getServers(): Observable<CustomResponse> {
    return this.http.get<CustomResponse>('http://localhost:8080/server/list')
  } */

  servers$ = <Observable<CustomResponse>>
    this.http.get<CustomResponse>(this.apiURL + '/server/list')
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      )

  save$ = (server: Server) => <Observable<CustomResponse>>
    this.http.post<CustomResponse>(this.apiURL + '/server/save', server)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      )

  ping$ = (ipAddress: string) => <Observable<CustomResponse>>
    this.http.get<CustomResponse>(this.apiURL + '/server/ping/' + ipAddress)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      )

  filter$ = (status: Status, response: CustomResponse) => <Observable<CustomResponse>>
    new Observable<CustomResponse>(
      suscriber => {
        console.log(response)
        suscriber.next(
          status === Status.ALL ? { ...response, message: "Server filtered by " + status + " status" } :
            {
              ...response,
              message: response.data.servers
                .filter(server => server.status === status).length > 0 ? status === Status.SERVER_UP ? "Server filtered by SERVER UP status" : "Server filtered by SERVER DOWN status" :
                "No servers of " + status + " found",
              data: { servers: response.data.servers.filter(server => server.status === status) },
            }
        )
        suscriber.complete()
      }
    )
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      )

  delete$ = (id: number) => <Observable<CustomResponse>>
    this.http.delete<CustomResponse>(this.apiURL + '/server/delete/' + id)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      )

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.log(error)
    return throwError(() => new Error('An error occured - Error code: ' + error.status + " / Error : " + error.message))
  }

}
