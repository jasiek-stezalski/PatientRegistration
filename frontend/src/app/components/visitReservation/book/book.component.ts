import {Component, EventEmitter, Output, ViewChild} from '@angular/core';
import {DayPilotModalComponent} from 'daypilot-pro-angular';
import {VisitModel} from '../../../models/visitModel.model';
import {Router} from '@angular/router';
import {Visit} from '../../../models/visit.model';
import {User} from '../../../models/user.model';
import {VisitService} from '../../../services/visit.services';
import {Clinic} from '../../../models/clinic.model';
import {isNull} from "util";

@Component({
  selector: 'book-dialog',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent {
  @ViewChild('modal') modal: DayPilotModalComponent;
  @Output() close = new EventEmitter();

  visit: Visit = new Visit();
  user: User = new User();
  clinic: Clinic = new Clinic();

  idOldVisit: number;

  constructor(private router: Router, private visitService: VisitService) {
    this.visit.user = new User();
    this.visit.visitModel = new VisitModel();
    this.visit.visitModel.user = new User();
  }

  show(visit: Visit, idVisitToChange?: number) {
    this.visit = visit;
    this.user = visit.visitModel.user;
    this.clinic = visit.visitModel.clinic;

    this.idOldVisit = idVisitToChange;

    this.modal.show();
  }

  submit() {
    let user: User = JSON.parse(sessionStorage.getItem('currentUser'));

    if (isNull(user)) {
      this.router.navigate(['login']);
      return;
    }



    this.visitService.getVisitsByIdUserAndDay(user.id, this.visit.start).subscribe(result => {

      if (isNaN(this.idOldVisit)) {

        if (result.length === 0
          || (result.length > 0
            && confirm('Masz w tym dniu zaplanowaną wizytę o ' + result.pop().start.substring(11, 16) +
              '. Czy na pewno chcesz zarezerwować tą wizytę?'))) {

          this.bookVisit(user);

        }
        else this.modal.hide();


      } else {
        if (result.length === 0
          || (result.length === 1 && result.pop().id === this.idOldVisit)
          || (result.length > 0
            && confirm('Masz w tym dniu zaplanowaną wizytę o ' + result.pop().start.substring(11, 16) +
              '. Czy na pewno chcesz zarezerwować tą wizytę?'))) {

          this.changeVisit();

        }
      }

    });

  }


  private bookVisit(user: User) {
    this.visitService.bookVisit(this.visit, user.id).subscribe(result => {
        this.visit.text = 'Zajęte';
        this.modal.hide(result);
      }, err => {
        this.modal.hide();

        if (err.valueOf().status === 409) {
          alert('Nie możesz być zapisany na dwie wizyty o tej samej specjalizacji jednocześnie!');
        } else if (err.valueOf().status === 406) {
          alert('W tym terminie masz już zaplanowaną wizytę!');
        } else if (err.valueOf().status === 404) {
          alert('Nie jesteś ubezpieczony. Nie możesz skorzystać z publicznej opieki medycznej!');
        }

        console.log(err);
      }
    );
  }

  private changeVisit() {
    this.visitService.changeVisit(this.visit, this.idOldVisit).subscribe(result => {
        this.visit.text = 'Zajęte';
        this.modal.hide(result);
      }, err => {
        this.modal.hide();

        if (err.valueOf().status === 406) {
          alert('W tym terminie masz już zaplanowaną wizytę!');
        }

        console.log(err);
      }
    );
  }

  cancel() {
    this.modal.hide();
  }

  closed(args) {
    this.close.emit(args);
  }

}
