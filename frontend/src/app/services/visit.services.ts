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

  getVisitsInWeek(from: DayPilot.Date, to: DayPilot.Date) {
    return this.http.get(this.url + '?from=' + from.toString() + '&to=' + to.toString()) as Observable<any>;
  }

  getVisitsInWeekByDoctor(from: DayPilot.Date, to: DayPilot.Date, idUser: string | number) {
    return this.http.get(this.url + 'doctor/' + idUser + '?from=' + from.toString() + '&to=' + to.toString()) as Observable<any>;
  }

  bookVisit(idVisit: String | Number, idUser: String | Number) {
    return this.http.get(this.url + 'book/' + idVisit + '?idUser=' + idUser);
  }

  moveVisit(data: any): Observable<Visit> {
    return this.http.put(this.url, data) as Observable<any>;
  }

  deleteVisit(idVisit: String): Observable<Visit> {
    return this.http.delete(this.url + idVisit) as Observable<any>;
  }

}


