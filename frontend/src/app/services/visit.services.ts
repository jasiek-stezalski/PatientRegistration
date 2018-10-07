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

  bookVisit(idVisit: String | Number, idUser: String | Number) {
    return this.http.get(this.url + 'book/' + idVisit + '?idUser=' + idUser);
  }

  deleteVisit(idVisit: String): Observable<Visit> {
    return this.http.delete(this.url + idVisit) as Observable<any>;
  }

}


