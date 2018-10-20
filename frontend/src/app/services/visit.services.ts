import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {DayPilot} from 'daypilot-pro-angular';
import {HttpClient} from '@angular/common/http';
import {Visit} from '../models/visit.model';
import {VisitFilter} from '../components/visitReservation/searchVisits/searchVisits.component';

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

  getVisitsByVisitFilter(visitFilter: VisitFilter): Observable<Visit[]> {
    return this.http.post(this.url + 'filter/', visitFilter) as Observable<Visit[]>
  }

  getHistoricalVisitsByIdUser(idUser: string | number): Observable<Visit[]> {
    return this.http.get(this.url + 'historical/user/' + idUser) as Observable<Visit[]>;
  }

  getActualVisitsByIdUser(idUser: string | number): Observable<Visit[]> {
    return this.http.get(this.url + 'actual/user/' + idUser) as Observable<Visit[]>;
  }

  bookVisit(visit: Visit, idUser: String | Number): Observable<Visit> {
    return this.http.put(this.url + 'book/?idUser=' + idUser, visit) as Observable<Visit>;
  }

  confirmVisit(visit: Visit): Observable<Visit> {
    return this.http.put(this.url + 'confirm/', visit) as Observable<Visit>;
  }

  moveVisit(visit: Visit): Observable<Visit> {
    return this.http.put(this.url, visit) as Observable<Visit>;
  }

  deleteVisit(idVisit: String) {
    return this.http.delete(this.url + idVisit);
  }

}


