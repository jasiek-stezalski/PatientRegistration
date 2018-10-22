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

  getVisitsInWeekByDoctor(from: DayPilot.Date, to: DayPilot.Date, idUser: string | number): Observable<Visit[]> {
    return this.http.get(this.url + 'doctor/' + idUser + '?from=' + from.toString() + '&to=' + to.toString()) as Observable<Visit[]>;
  }

  getVisitsByVisitFilter(careType: String, city: string, idClinic: string | number, specialization: String): Observable<Visit[]> {
    return this.http.get(this.url + 'filter/?careType=' + careType + '&city=' + city + '&idClinic=' + idClinic + '&specialization=' + specialization) as Observable<Visit[]>;
  }

  getVisitsByVisitFilterLimited(careType: string, city: string, idClinic: string | number, specialization: String): Observable<Visit[]> {
    return this.http.get(this.url + 'filterLimited/?careType=' + careType + '&city=' + city + '&idClinic=' + idClinic + '&specialization=' + specialization) as Observable<Visit[]>;
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


