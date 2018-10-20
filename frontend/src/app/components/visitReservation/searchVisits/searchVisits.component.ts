import {AfterViewInit, Component} from '@angular/core';
import {ClinicService} from "../../../services/clinic.service";
import {Clinic} from "../../../models/clinic.model";
import {UserService} from "../../../services/user.service";
import {User} from "../../../models/user.model";
import {VisitModelService} from "../../../services/visitModel.service";

@Component({
  selector: 'app-searchVisits',
  templateUrl: './searchVisits.component.html',
  styleUrls: ['./searchVisits.component.css']
})
export class SearchVisitsComponent implements AfterViewInit {

  visitFilter: VisitFilter = new VisitFilter();

  careType: String[] = ['Publiczna', 'Prywatna'];
  cities: Set<String> = new Set<String>();
  clinics: Clinic[] = [];
  doctors: User[] = [];
  specializations: String[] = this.visitModelService.specialization;


  constructor(private clinicService: ClinicService, private userService: UserService,
              private visitModelService: VisitModelService) {
  }

  ngAfterViewInit(): void {
    this.clinicService.getClinics()
      .subscribe(data => {
        this.clinics = data;
        data.forEach(i => this.cities.add(i.city));
      });

    this.userService.getUsersByRole('DOCTOR')
      .subscribe(data => this.doctors = data);
  }

  submit() {
    console.log(this.visitFilter);
  }

}

export class VisitFilter {
  careType?: string;
  city?: string;
  idClinic?: number;
  idDoctor?: number;
  specialization?: string;
  minPrice?: number;
  maxPrice?: number;
}
