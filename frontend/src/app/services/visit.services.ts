import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {DayPilot} from 'daypilot-pro-angular';
import {HttpClient} from '@angular/common/http';
import {Visit} from '../models/visit.model';

@Injectable()
export class VisitService {

  private url = 'http://localhost:8080/visits/';

  constructor(private http: HttpClient) {
  }

  getVisitsInWeek(from: DayPilot.Date, to: DayPilot.Date): Observable<Visit[]> {
    return this.http.get(this.url + '?from=' + from.toString() + '&to=' + to.toString()) as Observable<Visit[]>;
  }

  getVisitsInWeekByDoctor(from: DayPilot.Date, to: DayPilot.Date, idUser: string | number): Observable<Visit[]> {
    return this.http.get(this.url + 'doctor/' + idUser + '?from=' + from.toString() + '&to=' + to.toString()) as Observable<Visit[]>;
  }

  getHistoricalVisitsByIdUser(idUser: string | number): Observable<Visit[]> {
    return this.http.get(this.url + 'historical/user/' + idUser) as Observable<Visit[]>;
  }

  getActualVisitsByIdUser(idUser: string | number): Observable<Visit[]> {
    return this.http.get(this.url + 'actual/user/' + idUser) as Observable<Visit[]>;
  }

  bookVisit(idVisit: String | Number, idUser: String | Number): Observable<Visit> {
    return this.http.get(this.url + 'book/' + idVisit + '?idUser=' + idUser) as Observable<Visit>;
  }

  confirmVisit(idVisit: String | Number): Observable<Visit> {
    return this.http.get(this.url + 'confirm/' + idVisit) as Observable<Visit>;
  }

  moveVisit(data: Visit): Observable<Visit> {
    return this.http.put(this.url, data) as Observable<Visit>;
  }

  deleteVisit(idVisit: String) {
    return this.http.delete(this.url + idVisit);
  }

}


