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

  getVisitsByDoctor(idDoctor: number): Observable<Visit[]> {
    return this.http.get(this.url + 'idDoctor/' + idDoctor) as Observable<Visit[]>;
  }

  getVisitsInWeekByDoctor(from: DayPilot.Date, to: DayPilot.Date, idUser: string | number): Observable<Visit[]> {
    return this.http.get(this.url + 'doctor/' + idUser + '?from=' + from.toString() + '&to=' + to.toString()) as Observable<Visit[]>;
  }

  getVisitsByVisitFilter(careType: string, city: string, idClinic: string | number, specialization: string): Observable<Visit[]> {
    return this.http.get(this.url + 'filter/?careType=' + careType + '&city=' + city + '&idClinic=' + idClinic + '&specialization=' + specialization) as Observable<Visit[]>;
  }

  getVisitsByVisitFilterLimited(careType: string, city: string, idClinic: string | number, specialization: string): Observable<Visit[]> {
    return this.http.get(this.url + 'filterLimited/?careType=' + careType + '&city=' + city + '&idClinic=' + idClinic + '&specialization=' + specialization) as Observable<Visit[]>;
  }

  getHistoricalVisitsByIdUser(idUser: string | number): Observable<Visit[]> {
    return this.http.get(this.url + 'historical/user/' + idUser) as Observable<Visit[]>;
  }

  getActualVisitsByIdUser(idUser: string | number): Observable<Visit[]> {
    return this.http.get(this.url + 'actual/user/' + idUser) as Observable<Visit[]>;
  }

  getVisitsByIdUserAndDay(idUser: string | number, day: string): Observable<Visit[]> {
    return this.http.get(this.url + 'user/day/?idUser=' + idUser + '&day=' + day) as Observable<Visit[]>;
  }

  bookVisit(visit: Visit, idUser: string | Number): Observable<Visit> {
    return this.http.put(this.url + 'book/?idUser=' + idUser, visit) as Observable<Visit>;
  }

  bookVisitByDoctor(visit: Visit, idUser: string | Number): Observable<Visit> {
    return this.http.put(this.url + 'bookByDoctor/?idUser=' + idUser, visit) as Observable<Visit>;
  }

  changeVisit(newVisit: Visit, idOldVisit: number): Observable<Visit> {
    return this.http.put(this.url + 'change/?idOldVisit=' + idOldVisit, newVisit) as Observable<Visit>;
  }

  confirmVisit(visit: Visit): Observable<Visit> {
    return this.http.put(this.url + 'confirm/', visit) as Observable<Visit>;
  }

  moveVisit(visit: Visit): Observable<Visit> {
    return this.http.put(this.url, visit) as Observable<Visit>;
  }

  cancelVisit(visit: Visit): Observable<Visit> {
    return this.http.put(this.url + 'cancel/', visit) as Observable<Visit>;
  }

  deleteVisit(idVisit: string) {
    return this.http.delete(this.url + idVisit);
  }

  dataUpdate() {
    return this.http.get(this.url + 'dataUpdate/');
  }

}


