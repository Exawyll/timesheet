import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWeek, Week } from 'app/shared/model/week.model';
import { WeekService } from './week.service';

@Component({
  selector: 'jhi-week-update',
  templateUrl: './week-update.component.html',
})
export class WeekUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    label: [],
    monthNum: [],
    year: [],
    weekNum: [],
    isActive: [],
  });

  constructor(protected weekService: WeekService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ week }) => {
      this.updateForm(week);
    });
  }

  updateForm(week: IWeek): void {
    this.editForm.patchValue({
      id: week.id,
      label: week.label,
      monthNum: week.monthNum,
      year: week.year,
      weekNum: week.weekNum,
      isActive: week.isActive,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const week = this.createFromForm();
    if (week.id !== undefined) {
      this.subscribeToSaveResponse(this.weekService.update(week));
    } else {
      this.subscribeToSaveResponse(this.weekService.create(week));
    }
  }

  private createFromForm(): IWeek {
    return {
      ...new Week(),
      id: this.editForm.get(['id'])!.value,
      label: this.editForm.get(['label'])!.value,
      monthNum: this.editForm.get(['monthNum'])!.value,
      year: this.editForm.get(['year'])!.value,
      weekNum: this.editForm.get(['weekNum'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWeek>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
